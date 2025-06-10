package org.joker.test_jenkins;

import com.alibaba.fastjson2.JSON;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gesi at 15:31 on 2025/6/10
 */
@RestController
@RequestMapping("/api/v1")
public class MockController {

    @GetMapping("/employee")
    public List<Employee> getEmployees() throws IOException {
        ClassLoader classLoader = MockController.class.getClassLoader();
        InputStream is = null;
        try {
            is = classLoader.getResourceAsStream("demo_cross_chart_employee.csv");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(br)
                    .withType(Employee.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}
