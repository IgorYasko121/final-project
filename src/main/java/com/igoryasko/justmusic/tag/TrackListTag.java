package com.igoryasko.justmusic.tag;

import com.igoryasko.justmusic.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * The class {@code TrackListTag} provides teg for JSP to show a list of tracks.
 * @author Igor Yasko on 2019-07-19.
 */
public class TrackListTag extends TagSupport {

    private List<Track> tracks;

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        if (tracks != null && !tracks.isEmpty()) {
            try {
                for (Track track : tracks) {
                    out.write("<p>" + track.getName() + " " + track.getSinger().getName() + " " + track.getGenre().getName() + "</p>");
                    out.write("<audio controls=\"controls\">");
                    out.write("<source src=" + track.getPath() + " type=\"audio/mpeg\">" + "</audio>");
                    out.write("</audio>");
                }
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }

}
