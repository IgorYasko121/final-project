package com.igoryasko.justmusic.controller;

import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.language.LanguageManager;
import com.igoryasko.justmusic.service.AdminService;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
import com.igoryasko.justmusic.validator.TrackValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

import static com.igoryasko.justmusic.util.ParameterConstant.LOCALE;

/**
 * The class {@code FileLoadServlet} uploads music to the server.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
@WebServlet(name = "FileLoadServlet", urlPatterns = {"/upload"}, loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileLoadServlet extends HttpServlet {

    private static final long serialVersionUID = 2098317608976171919L;

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;
        File uploadFolder = new File(uploadFilePath);
        AdminService adminService = new AdminService();
        TrackService trackService = new TrackService();
        TrackValidator validator = new TrackValidator();

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String trackName = request.getParameter(ParameterConstant.TRACK_NAME);
        String genreName = request.getParameter(ParameterConstant.GENRE).toUpperCase();
        String singerName = request.getParameter(ParameterConstant.SINGER);

        Part part = request.getPart("file");
        if (part != null && part.getSize() > 0) {
            String fileName = part.getSubmittedFileName();
            String contentType = part.getContentType();
            String fileNameDb = UPLOAD_DIR + File.separator + fileName;

            if (contentType.equalsIgnoreCase("audio/mp3")) {
                part.write(uploadFilePath + File.separator + fileName);
                try {
                    if (validator.validate(trackName, genreName, singerName)) {
                        if (!trackService.checkTrack(fileNameDb)) {
                            adminService.addTrack(trackName, fileNameDb, genreName, singerName);
                        }else {
                            request.setAttribute(AttributeConstant.ERROR_INPUT_MESSAGE,
                                    LanguageManager.getMessage("track.exist", (String) request.getSession().getAttribute(LOCALE)));
                            request.getRequestDispatcher(PageConstant.PATH_ADMIN).forward(request, response);
                            return;
                        }
                    } else {
                        request.getSession().setAttribute(AttributeConstant.ERROR_INPUT_MESSAGE,
                                LanguageManager.getMessage("input.failed", (String) request.getSession().getAttribute(LOCALE)));
                        request.getRequestDispatcher(PageConstant.PATH_ADMIN).forward(request, response);
                        return;
                    }
                } catch (ServiceException e) {
                    log.error("ServiceException", e);
                    request.setAttribute(AttributeConstant.ERROR, e);
                    request.getRequestDispatcher(PageConstant.PAGE_SERVER_ERROR).forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + PageConstant.PATH_ADMIN);
        }
    }

}
