package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.AuthorModel;
import java.util.List;

public interface GetAuthorsByWorkCenterPort {

    List<AuthorModel> getByWorkCenter(String workCenter);

}