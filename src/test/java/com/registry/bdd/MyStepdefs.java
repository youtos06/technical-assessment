package com.registry.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.registry.technicalassessment.TechnicalAssessmentApplication;
import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.holder.ApiPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechnicalAssessmentApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestConfiguration.class})
public class MyStepdefs {

    @Autowired
    MockMvc mvc;

    ResultActions action;

    UserDto userDto;


    @Given("a new user")
    public void aNewUser() {
        userDto = new UserDto();
    }

    @Then("register the user")
    public void iShouldRegisterUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        action = mvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
    }

    @And("fill in {string} with {string}")
    public void iFillInWith(String arg0, String arg1) {
        if (arg0.equals("Name")){
            userDto.setName(arg1);
        }
        if (arg0.equals("Birth Date")){
            userDto.setBirthDate(LocalDate.parse(arg1));
        }
        if (arg0.equals("Gender")){
            userDto.setGender(arg1);
        }
        if (arg0.equals("Country")){
            userDto.setCountry(arg1);
        }
    }

    @And("Receive status code of {int}")
    public void receiveStatusCodeOf(int arg0) throws Exception {
        action.andExpect(status().is(arg0));
    }

    @And("Field {string} equals {string}")
    public void receiveNameOf(String property,String value) throws Exception {
        action.andExpect(jsonPath("$."+property).value(value));
    }

}
