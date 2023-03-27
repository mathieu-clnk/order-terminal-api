package com.kamvity.samples.otm.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Response", description = "Generic response")
public abstract class Response {

    @Schema(name = "SUCCESS",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public static final String SUCCESS = "success";

    @Schema(name = "FAILED",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public static final String FAILED = "failed";

    @Schema(name = "status", pattern = "^((\\bsuccess\\b)|(\\bfailed\\b))$", example = "success|failed")
    public String status;

    @Schema(name = "errorMessage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String errorMessage;

    @Schema(name = "sensitiveMessage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String sensitiveMessage;
}
