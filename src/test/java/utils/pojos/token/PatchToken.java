package utils.pojos.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class PatchToken {
    private String platform;
    private String version;
    private String build;
}