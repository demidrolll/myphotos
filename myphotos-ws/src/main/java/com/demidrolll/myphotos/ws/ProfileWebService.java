package com.demidrolll.myphotos.ws;

import com.demidrolll.myphotos.ws.model.ProfilePhotosSoap;
import com.demidrolll.myphotos.ws.model.ProfileSoap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static com.demidrolll.myphotos.ws.Constants.WS_NAMESPACE;

@WebService(targetNamespace = WS_NAMESPACE + "ProfileService?wsdl")
public interface ProfileWebService {

    @WebMethod
    @WebResult(name = "profile")
    ProfileSoap findById(
            @WebParam(name = "id") Long id,
            @WebParam(name = "withPhotos") boolean withPhotos,
            @WebParam(name = "limit") int limit
    );

    @WebMethod
    @WebResult(name = "profilePhotos")
    ProfilePhotosSoap findProfilePhotos(
            @WebParam(name = "profileId") Long profileId,
            @WebParam(name = "page") int page,
            @WebParam(name = "limit") int limit
    );
}
