package ca.cmpt213.asn5.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.cmpt213.asn5.demo.models.ResponseModel;
import ca.cmpt213.asn5.demo.models.Superhuman;
import ca.cmpt213.asn5.demo.service.SuperhumanService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class SuperhumanController {

    @Autowired
    SuperhumanService superhumanService;
    
    @PostMapping("/superhuman")
    public ResponseModel addSuperhuman(@RequestBody Superhuman superhuman, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return superhumanService.addSuperhuman(superhuman);
    }

    @GetMapping("/superhuman/all")
    public ResponseModel fetchAllSuperhumans() {
        return superhumanService.fetchAllSuperhumans();
    }

    @GetMapping("/superhuman/{sid}")
    public ResponseModel getSuperhumanInfo(@PathVariable long sid) {
        return superhumanService.getSuperhumanInfo(sid);
    }

    @DeleteMapping("/superhuman/{sid}")
    public ResponseModel deleteSuperhero(@PathVariable long sid, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return superhumanService.deleteSuperhero(sid);
    }
}
