package com.demidrolll.myphotos.ws;

import com.demidrolll.myphotos.ws.model.ImageLinkSoap;
import com.demidrolll.myphotos.ws.model.PhotosSoap;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import static com.demidrolll.myphotos.ws.Constants.WS_NAMESPACE;

@WebService(targetNamespace = WS_NAMESPACE + "PhotoService?wsdl")
public interface PhotoWebService {

    @WebMethod
    @WebResult(name = "photos")
    PhotosSoap findAllOrderByPhotoPopularity(
            @WebParam(name = "page") int page,
            @WebParam(name = "limit") int limit,
            @WebParam(name = "withTotal") boolean withTotal
    );

    @WebMethod
    @WebResult(name = "photos")
    PhotosSoap findAllOrderByAuthorPopularity(
            @WebParam(name = "page") int page,
            @WebParam(name = "limit") int limit,
            @WebParam(name = "withTotal") boolean withTotal
    );

    @WebMethod
    @WebResult(name = "imageLink")
    ImageLinkSoap viewLargePhoto(
            @WebParam(name = "photoId") Long photoId
    );

    @WebMethod
    @WebResult(name = "originalImage")
    DataHandler downloadOriginalImage(
            @WebParam(name = "photoId") Long photoId
    );
}
