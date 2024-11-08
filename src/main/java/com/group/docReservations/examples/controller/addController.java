package com.group.docReservations.examples.controller;//package com.group.docReservations.controller;
//
//import com.group.docReservations.classes.*;
//import com.group.docReservations.repository.*;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class addController {
//
//    @RequestMapping("/admin")
//    public String admin() {
//        return "admin_panel";
//    }
//
//    @GetMapping(path = "admin/addServer")
//    public String server(Model model) {
//        return addEntityForm(model, new Server(), "add/add_server");
//    }
//
//    @PostMapping(value = "admin/addServer/save")
//    public String addServer(@Valid @ModelAttribute("server") Server server, BindingResult errors, Model model) throws Exception {
//        return saveEntity(server, errors, model, serverRepository, "add/add_server", "redirect:/admin/addServer?success");
//    }
//
//    @GetMapping("/admin/editServer/{id}")
//    public String editServer(@PathVariable("id") long id, Model model) {
//        return editEntity(id, model, serverRepository, "edit/edit_server", "servers");
//    }
//
//    @PostMapping("/admin/updateServer/{id}")
//    public String updateServer(@PathVariable("id") long id, @Valid Server server, BindingResult result, Model model) {
//        return updateEntity(id, server, result, model, serverRepository, "edit/edit_server", "redirect:/panel/server");
//    }
//
//    @GetMapping("/admin/deleteServer/{id}")
//    public String deleteServer(@PathVariable("id") long id, Model model) {
//        return deleteEntity(id, model, serverRepository, "redirect:/panel/server");
//    }
//
//    private String addEntityForm(Model model, Object entity, String viewName) {
//        model.addAttribute("entity", entity);
//        return viewName;
//    }
//
//    private <T> String saveEntity(@Valid @ModelAttribute("entity") T entity, BindingResult errors, Model model,
//                                  JpaRepository<T, Long> repository, String errorViewName, String successViewName) {
//        if (errors.hasErrors()) {
//            return errorViewName;
//        } else {
//            repository.save(entity);
//            return successViewName;
//        }
//    }
//
//    private <T> String editEntity(Long id, Model model, JpaRepository<T, Long> repository, String viewName, String attributeName) {
//        T entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
//        model.addAttribute(attributeName, entity);
//        return viewName;
//    }
//
//    private <T> String updateEntity(Long id, @Valid T entity, BindingResult result, Model model,
//                                    JpaRepository<T, Long> repository, String errorViewName, String successViewName) {
//        if (result.hasErrors()) {
//            return errorViewName;
//        } else {
//            repository.save(entity);
//            return successViewName;
//        }
//    }
//
//    private <T> String deleteEntity(Long id, Model model, JpaRepository<T, Long> repository, String redirectViewName) {
//        T entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
//        repository.delete(entity);
//        return redirectViewName;
//    }
//}