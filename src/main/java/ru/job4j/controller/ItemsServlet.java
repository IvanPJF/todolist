package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Item;
import ru.job4j.service.Dispatcher;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ItemsServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();
    private static final Dispatcher DISPATCHER = Dispatcher.getInstance().init();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (final PrintWriter writer = resp.getWriter()) {
            new ObjectMapper().writeValue(writer, SERVICE.allItems());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        String itemJSON = req.getParameter("item");
        ObjectMapper jsonMapper = new ObjectMapper();
        Item item = jsonMapper.readValue(itemJSON, Item.class);
        DISPATCHER.send(action, SERVICE, item);
        try (final PrintWriter writer = resp.getWriter()) {
            jsonMapper.writeValue(writer, item);
        }
    }
}
