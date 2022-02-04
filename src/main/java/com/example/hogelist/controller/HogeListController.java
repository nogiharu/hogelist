package com.example.hogelist.controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import com.example.hogelist.common.OpMsg;
import com.example.hogelist.dao.HogeDaoImpl;
import com.example.hogelist.entity.HogeTable;

import com.example.hogelist.form.HogeData;
import com.example.hogelist.form.HogeQuery;
import com.example.hogelist.repository.HogeRepository;
import com.example.hogelist.service.HogeService;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HogeListController {
    private final HogeRepository repository;
    private final HogeService service;
    private final HttpSession session;
    private final MessageSource message;

    @PersistenceContext
    private EntityManager manager;
    HogeDaoImpl hogeDaoImpl;

    @PostConstruct
    public void init() {
        hogeDaoImpl = new HogeDaoImpl(manager);
    }

    // 【SELECT】画面遷移。全件一覧表示
    @GetMapping("/hoge")
    public String showHogeList(@PageableDefault(page = 0, size = 5) Pageable pageable, HogeQuery hogeQuery,
            Model model) {
        hogeQuery = (HogeQuery) session.getAttribute("hogeQuery");
        if (hogeQuery == null) {
            hogeQuery = new HogeQuery();
            session.setAttribute("hogeQuery", hogeQuery);
        }
        Pageable prevPageable = (Pageable) session.getAttribute("prevPageable");
        if (prevPageable == null) {
            prevPageable = pageable;
            session.setAttribute("prevPageable", prevPageable);
        }
        Page<HogeTable> hogeList = hogeDaoImpl.findByCriteria(prevPageable, hogeQuery);
        model.addAttribute("hogeList", hogeList);
        return "hogeList";
    }

    // 【INSERT】画面遷移。
    @GetMapping("/hoge/create")
    public String createHoge(HogeData hogeData) {
        session.setAttribute("mode", "create");
        return "hogeForm";
    }

    // 【INSERT】登録処理。
    @PostMapping("/hoge/create")
    public String createHoge(@Validated HogeData hogeData, BindingResult result,
            RedirectAttributes redirectAttributes, Model model, Locale locale) {
        // System.out.println(hogeData);
        boolean isValid = service.isValid(hogeData, result, true, locale);
        if (!result.hasErrors() && isValid) {
            HogeTable hogeList = hogeData.toEntity();
            repository.saveAndFlush(hogeList);
            String msg = message.getMessage("msg.i.created", null, locale);
            redirectAttributes.addFlashAttribute("msg", new OpMsg("I", msg));
            return "redirect:/hoge";
        } else {
            String msg = message.getMessage("msg.e.something_wrong", null, locale);
            model.addAttribute("msg", new OpMsg("E", msg));
            return "hogeForm";
        }
    }

    // 【SELECT】
    @PostMapping("/hoge/cancel")
    public String cancelHoge() {
        return "redirect:/hoge";
    }

    // 【UPDATE】画面遷移
    @GetMapping("/hoge/{id}")
    public String upateTodo(@PathVariable(name = "id") int id, Model model) {
        HogeTable hogeForm = repository.findById(id).get();
        model.addAttribute("hogeData", hogeForm);
        session.setAttribute("mode", "update");
        return "hogeForm";
    }

    // 【UPDATE】登録処理
    @PostMapping("/hoge/update")
    public String updateHoge(@Validated HogeData hogeData, BindingResult result,
            RedirectAttributes redirectAttributes, Model model, Locale locale) {
        boolean isValid = service.isValid(hogeData, result, false, locale);
        if (!result.hasErrors() && isValid) {
            HogeTable hogeList = hogeData.toEntity();
            repository.saveAndFlush(hogeList);
            String msg = message.getMessage("msg.i.updated", null, locale);
            redirectAttributes.addFlashAttribute("msg", new OpMsg("I", msg));
            return "redirect:/hoge";
        } else {
            String msg = message.getMessage("msg.e.something_wrong", null, locale);
            model.addAttribute("msg", new OpMsg("E", msg));
            return "hogeForm";
        }

    }

    // 【DELETE】
    @PostMapping("/hoge/delete")
    public String deleteHoge(HogeData hogeData,
            RedirectAttributes redirectAttributes, Locale locale) {
        repository.deleteById(hogeData.getId());
        String msg = message.getMessage("msg.i.deleted", null, locale);
        redirectAttributes.addFlashAttribute("msg", new OpMsg("I", msg));
        return "redirect:/hoge";
    }

    // 【QUERY】
    @PostMapping("/hoge/query")
    public String queryHoge(@PageableDefault(page = 0, size = 5) Pageable pageable, @Validated HogeQuery hogeQuery,
            BindingResult result, Model model, Locale locale) {
        session.invalidate();
        if (!result.hasErrors() && service.isValid(hogeQuery, result, locale)) {
            Page<HogeTable> hogeList = hogeDaoImpl.findByCriteria(pageable, hogeQuery);
            model.addAttribute("hogeList", hogeList);
            session.setAttribute("hogeQuery", hogeQuery);
            if (hogeList.getContent().size() == 0) {
                String msg = message.getMessage("msg.w.not_found", null, locale);
                model.addAttribute("msg", new OpMsg("W", msg));
            }
            return "hogeList";
        } else {
            String msg = message.getMessage("msg.e.something_wrong", null, locale);
            model.addAttribute("msg", new OpMsg("E", msg));
            return "hogeList";
        }

    }

    // 【QUERY】
    @GetMapping("/hoge/query")
    public String queryHoge(@PageableDefault(page = 0, size = 5) Pageable pageable, HogeQuery hogeQuery, Model model) {
        hogeQuery = (HogeQuery) session.getAttribute("hogeQuery");
        Page<HogeTable> hogeList = hogeDaoImpl.findByCriteria(pageable, hogeQuery);
        model.addAttribute("hogeList", hogeList);
        session.setAttribute("prevPageable", pageable);
        return "hogeList";
    }
}