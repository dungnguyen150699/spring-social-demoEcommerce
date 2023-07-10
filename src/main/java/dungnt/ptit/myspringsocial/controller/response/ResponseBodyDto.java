package dungnt.ptit.myspringsocial.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dungnt.ptit.myspringsocial.controller.response.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBodyDto<E> {

    /*
     * =============================================================================
     * =================================== ===== PRIVATE PROPERTIES =====
     * =============================================================================
     * ===================================
     */

    /**
     * Reset API: Response code
     */
    private ResponseCode code;

    /**
     * Reset API: Response message
     */
    private String message;

    /**
     * Reset API: Response total items for pagination
     */

    @JsonProperty("total_items")
    private Long totalItems;

    /**
     * Reset API: Response page number for pagination
     */
    private Integer page;

    /**
     * Reset API: Response size of one page for pagination
     */
    private Integer size;

    /**
     * Reset API: Response data of 1 item
     */
    private E item;

    /**
     * Reset API: Response data of list items
     */
    private List<E> items;

    public ResponseBodyDto(Pageable pageable, Page<E> page, ResponseCode code) {
        this.code = code;
        this.totalItems = page.getTotalElements();
        this.page = pageable.getPageNumber();
        this.size = pageable.getPageSize();
        this.items = page.getContent();
    }

    public ResponseBodyDto(E item, ResponseCode code) {
        this.code = code;
        this.item = item;
    }

    public ResponseBodyDto(List<E> items, ResponseCode code, Long totalItems) {
        this.code = code;
        this.items = items;
        this.totalItems = totalItems;
    }

    public ResponseBodyDto(ResponseCode code) {
        this.code = code;
    }
}