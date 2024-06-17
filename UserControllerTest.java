package com.todo.user.junit;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.user.UserMicroserviceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserMicroserviceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	private ObjectMapper mapper = new ObjectMapper();

	private Resource casesFile;

	private Map<String, Json> cases;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		casesFile = new ClassPathResource("cases.json");

		cases = mapper.readValue(casesFile.getInputStream(), new TypeReference<Map<String, Json>>() {
		});
	}

	@Test
	public void registrationTest() throws Exception {
		Json json = cases.get("registration");
		test(json);
	}
	
	@Test
	public void loginSuccessfullTest() throws Exception {
		Json json = cases.get("login");
		test(json);
	}
	@Test
	public void userNameBlank() throws Exception {
		Json json = cases.get("userregsad1");
		test(json);
	}
	@Test
	public void emailBlank() throws Exception {
		Json json = cases.get("userRegsad4");
		test(json);
	}
	@Test
	public void mobileNoBlank() throws Exception {
		Json json = cases.get("userRegsad5");
		test(json);
	}
	@Test
	public void passwordBlank() throws Exception {
		Json json = cases.get("userRegsad6");
		test(json);
	}
	@Test
	public void passwordvalid() throws Exception {
		Json json = cases.get("userRegsad9");
		test(json);
	}
	@Test
	public void passwordvalid2() throws Exception {
		Json json = cases.get("userRegsad10");
		test(json);
	}
	@Test
	public void passwordvalid3() throws Exception {
		Json json = cases.get("userRegsad11");
		test(json);
	}
	@Test
	public void passwordvalid4() throws Exception {
		Json json = cases.get("userRegsad12");
		test(json);
	}@Test
	public void mobileValidation() throws Exception {
		Json json = cases.get("userRegsad13");
		test(json);
	}
	@Test
	public void emailValid() throws Exception {
		Json json = cases.get("userRegsad14");
		test(json);
	}
	@Test
	public void emailValidationdb() throws Exception {
		Json json = cases.get("userRegsad15");
		test(json);
	}
	@Test
	public void usernameMinValidation() throws Exception {
		Json json = cases.get("userRegsad16");
		test(json);
	}
	@Test
	public void usernameMaxValidation() throws Exception {
		Json json = cases.get("userRegsad17");
		test(json);
	}
	
	/*@Test
	public void activateAcc() throws Exception {
		Json json = cases.get("activateaccount");
		test(json);
	}*/
	@Test
	public void loginsad() throws Exception {
		Json json = cases.get("loginsad");
		test(json);
	}
	@Test
	public void loginsad3() throws Exception {
		Json json = cases.get("loginsad3");
		test(json);
	}
	@Test
	public void loginsad5() throws Exception {
		Json json = cases.get("loginsad5");
		test(json);
	}
	@Test
	public void loginsad7() throws Exception {
		Json json = cases.get("loginsad7");
		test(json);
	}
	/*@Test
	public void resetpasswordTest() throws Exception {
		Json json = cases.get("resetpassword");
		test(json);
	}*/
	@Test
	public void forgotpasswordTest() throws Exception {
		Json json = cases.get("forgotpassword");
		test(json);
	}
	@Test
	public void getUsers() throws Exception
	{
		Json json=cases.get("userlist");
		test(json);
	}

	private void test(Json json) throws Exception {
		ResultActions actions = mockMvc
				.perform(getMethod(json).headers(json.getRequest().getHeaders()).contentType(MediaType.APPLICATION_JSON)
						.content(getRequestBody(json)).accept(MediaType.APPLICATION_JSON));

		actions.andExpect(status().is(json.getResponse().getStatus().value()));

		MockHttpServletResponse response = actions.andReturn().getResponse();

		for (String key : json.getResponse().getHeaders().keySet()) {
			assertEquals(json.getResponse().getHeaders().get(key), response.getHeader(key));
		}
		assertEquals(getResponseBody(json), response.getContentAsString());
	}

	private MockHttpServletRequestBuilder getMethod(Json json) {
		return MockMvcRequestBuilders.request(HttpMethod.resolve(json.getRequest().getMethod()),
				json.getRequest().getUrl());
	}

	private String getRequestBody(Json json) throws JsonProcessingException {
		return mapper.writeValueAsString(json.getRequest().getBody());
	}

	private String getResponseBody(Json json) throws JsonProcessingException {
		return mapper.writeValueAsString(json.getResponse().getBody());
	}
}
