package com.joanna.footmessage.modles.repositories;

import com.joanna.footmessage.modles.entities.Question;
import com.joanna.footmessage.modles.entities.User;
import com.joanna.footmessage.modles.models.ModifyUserInformationModel;
import com.joanna.footmessage.modles.models.ResponseModel;
import com.joanna.footmessage.modles.models.SignInModel;
import com.joanna.footmessage.modles.models.SignUpModel;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    ResponseModel<User> signIn(SignInModel signInModel) throws IOException;

    ResponseModel<User> signUp(SignUpModel signUpModel) throws IOException;

    ResponseModel<List<Question>> getHealthQuestions();

    ResponseModel<User> modifyUserInformation(ModifyUserInformationModel modifyUserInformationModel);
}
