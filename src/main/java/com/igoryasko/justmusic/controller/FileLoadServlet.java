package com.igoryasko.justmusic.controller;

import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.AdminService;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
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

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;
        File uploadFolder = new File(uploadFilePath);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        Part part = request.getPart("file");
        if (part != null && part.getSize() > 0) {
            String fileName = part.getSubmittedFileName();
            String contentType = part.getContentType();
            if (contentType.equalsIgnoreCase("audio/mp3")) {
                part.write(uploadFilePath + File.separator + fileName);
                try {
                    processRequest(request, fileName);
                } catch (ServiceException e) {
                    log.error("Can't edd track to db", e);
                    e.printStackTrace();
                    request.setAttribute(AttributeConstant.ERROR, e);
                    request.getRequestDispatcher(PageConstant.PAGE_SERVER_ERROR).forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + PageConstant.PATH_ADMIN);
        }
    }

    private void processRequest(HttpServletRequest request, String fileName) throws ServiceException {
        String fileNameDb = UPLOAD_DIR + File.separator + fileName;
        String trackName = request.getParameter(ParameterConstant.TRACK_NAME);
        String genreName = request.getParameter(ParameterConstant.GENRE).toUpperCase();
        String singerName = request.getParameter(ParameterConstant.SINGER);
        AdminService adminService = new AdminService();
        TrackService trackService = new TrackService();
        Singer singer = adminService.findSingerByName(singerName);
        if (singer == null) {
            singer = new Singer(singerName);
            long singerId = adminService.addSinger(singer.getName());
            singer.setSingerId(singerId);
        }
        Genre genre = adminService.findGenreByName(genreName);
        if (genre == null) {
            genre = new Genre(genreName);
            long genreId = adminService.addGenre(genre.getName());
            genre.setGenreId(genreId);
        }
        trackService.addTrack(trackName, fileNameDb, genre, singer);
    }

}
