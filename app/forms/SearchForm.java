package forms;

import org.apache.commons.lang3.Range;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchForm {
    @Constraints.Required
    public Integer guests;
    @Constraints.Required
    public LocalDate startDate;
    @Constraints.Required
    public LocalDate endDate;
    @Constraints.Required
    public Integer roomStandard;
    @Constraints.Required
    public Range<BigDecimal> priceRange;

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        return errors;
    }
}
