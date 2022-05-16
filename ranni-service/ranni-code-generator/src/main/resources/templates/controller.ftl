package ${basePackage}.${controllerPackage};

import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${servicePackage}.${className}Service;
import com.ymchen.rannibase.entity.base.PageRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


/**
 * ${tableComment} Controller
 *
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@Validated
@RestController
@RequestMapping("${className?uncap_first}")
@RequiredArgsConstructor
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;


    @GetMapping("list")
    public Object ${className?uncap_first}List(PageRequest request, ${className} ${className?uncap_first}) {
        return this.${className?uncap_first}Service.find${className}s(request, ${className?uncap_first});
    }

    @PostMapping
    public void add${className}(@Valid ${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.create${className}(${className?uncap_first});
    }

    @DeleteMapping
    public void delete${className}(${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.delete${className}(${className?uncap_first});
    }

    @PutMapping
    public void update${className}(${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.update${className}(${className?uncap_first});
    }
}
