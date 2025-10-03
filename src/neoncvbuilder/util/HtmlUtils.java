package neoncvbuilder.util;

import neoncvbuilder.model.CVData;
import neoncvbuilder.model.PersonalInfo;

public class HtmlUtils {

    public static String buildHtml(CVData cv) {
        if (cv == null) cv = new CVData();
        PersonalInfo p = cv.getPersonalInfo();
        if (p == null) p = new PersonalInfo();

        String fullName = safe(p.getFullName());
        String email = safe(p.getEmail());
        String phone = safe(p.getPhone());
        String address = safe(p.getAddress());
        String summary = safe(p.getSummary());

        String skills = safe(cv.getSkills());
        String experience = safe(cv.getExperience());
        String education = safe(cv.getEducation());
        String projects = safe(cv.getProjects());
        String certifications = safe(cv.getCertifications());
        String avatarPath = safe(cv.getAvatarPath());

        if (avatarPath.isEmpty()) {
            avatarPath = "https://via.placeholder.com/120";
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='utf-8'><title>CV</title><style>")
            .append("body{background:#ffffff;color:#000000;font-family:Arial,sans-serif;padding:20px;}")
            .append(".section{border:2px solid #00ffff;padding:20px;margin-bottom:20px;border-radius:10px;")
            .append("box-shadow:0 0 10px #00ffff;background-color:#f9f9f9;}")
            .append("h2{color:#00aaff;} h3{color:#0077cc;} img{width:120px;height:120px;border-radius:50%;border:2px solid #00ffff;}")
            .append("p, pre{font-size:14px;line-height:1.6;}")
            .append("</style></head><body>");

        html.append("<div class='section'><img src='").append(escape(avatarPath)).append("'><h2>")
            .append(escape(fullName)).append("</h2><p>")
            .append(escape(email)).append(" | ").append(escape(phone)).append(" | ").append(escape(address))
            .append("</p></div>");

        html.append("<div class='section'><h3>Tóm tắt</h3><p>").append(nl2br(summary)).append("</p></div>");
        html.append("<div class='section'><h3>Kỹ năng</h3><pre>").append(nl2br(skills)).append("</pre></div>");
        html.append("<div class='section'><h3>Kinh nghiệm</h3><pre>").append(nl2br(experience)).append("</pre></div>");
        html.append("<div class='section'><h3>Học vấn</h3><pre>").append(nl2br(education)).append("</pre></div>");
        html.append("<div class='section'><h3>Dự án</h3><pre>").append(nl2br(projects)).append("</pre></div>");
        html.append("<div class='section'><h3>Chứng chỉ</h3><pre>").append(nl2br(certifications)).append("</pre></div>");

        html.append("</body></html>");
        return html.toString();
    }

    private static String safe(String s) {
        return (s == null) ? "" : s.trim();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static String nl2br(String s) {
        if (s == null || s.isEmpty()) return "";
        return escape(s).replace("\r\n", "<br>").replace("\n", "<br>").replace("\r", "<br>");
    }
}