package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/cleanup-default")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> cleanupDefault() {
        List<String> attempted = List.of("codigo", "direccion");
        List<String> cleared = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String col : attempted) {
            try {
                String sql = String.format("UPDATE usuario SET %s = NULL", col);
                jdbcTemplate.update(sql);
                cleared.add(col);
            } catch (BadSqlGrammarException ex) {
                missing.add(col);
            } catch (DataAccessException ex) {
                missing.add(col);
            }
        }

        return ResponseEntity.ok(Map.of("cleared", cleared, "missingOrFailed", missing));
    }

    @PostMapping("/cleanup")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> cleanup(@RequestBody Map<String, Object> body) {
        Object colsObj = body.get("columns");
        if (!(colsObj instanceof List)) {
            return ResponseEntity.badRequest().body(Map.of("error", "columns must be an array"));
        }
        List<?> cols = (List<?>) colsObj;
        List<String> cleared = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (Object c : cols) {
            if (!(c instanceof String))
                continue;
            String col = (String) c;
            try {
                String sql = String.format("UPDATE usuario SET %s = NULL", col);
                jdbcTemplate.update(sql);
                cleared.add(col);
            } catch (BadSqlGrammarException ex) {
                missing.add(col);
            } catch (DataAccessException ex) {
                missing.add(col);
            }
        }
        return ResponseEntity.ok(Map.of("cleared", cleared, "missingOrFailed", missing));
    }
}
