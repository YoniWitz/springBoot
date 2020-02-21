package com.yoniwitz.app.ui.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoniwitz.app.ui.model.request.UserLoginRequestModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("users")
public class LoginController {

	@ApiOperation(value = "${loginController.Swagger.Login.value}", notes = "${loginController.Swagger.Login.notes}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Response Headers", responseHeaders = {
			@ResponseHeader(name = "authorization", description = "Bearer <JWT value here>", response = String.class),
			@ResponseHeader(name = "userId", description = "<Public User Id value here", response = String.class)

			}) })
	@PostMapping(path = "/login", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public void fakeLogin(@RequestBody UserLoginRequestModel userLoginRequestModel) {
		throw new IllegalStateException(
				"This method should not be called. The method is implemented by Spring Security");
	}
}
