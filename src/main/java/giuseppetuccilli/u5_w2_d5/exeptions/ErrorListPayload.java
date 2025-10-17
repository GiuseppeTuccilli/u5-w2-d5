package giuseppetuccilli.u5_w2_d5.exeptions;

import java.util.List;

public record ErrorListPayload(String message, List<String> erList) {
}
