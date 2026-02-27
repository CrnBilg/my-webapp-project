package com.example.todo;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/tasks/*")
public class TasksServlet extends HttpServlet {
    private TaskDAO dao;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        if (path == null || "/".equals(path)) {
            List<Task> list = dao.findAll();
            mapper.writeValue(out, list);
            return;
        }
        String[] parts = path.split("/");
        if (parts.length >= 2) {
            try {
                int id = Integer.parseInt(parts[1]);
                Task t = dao.findById(id);
                if (t == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{}");
                    return;
                }
                mapper.writeValue(out, t);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Task t = mapper.readValue(req.getInputStream(), Task.class);
        Task created = dao.create(t);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        mapper.writeValue(resp.getWriter(), created);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        if (path == null || "/".equals(path)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] parts = path.split("/");
        if (parts.length >= 2) {
            try {
                int id = Integer.parseInt(parts[1]);
                Task t = mapper.readValue(req.getInputStream(), Task.class);
                t.setId(id);
                boolean ok = dao.update(t);
                if (ok) {
                    mapper.writeValue(resp.getWriter(), t);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");
        if (path == null || "/".equals(path)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] parts = path.split("/");
        if (parts.length >= 2) {
            try {
                int id = Integer.parseInt(parts[1]);
                boolean ok = dao.delete(id);
                if (ok) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
