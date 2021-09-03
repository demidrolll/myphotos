package com.demidrolll.myphotos.ws.bean;

import static com.demidrolll.myphotos.ws.Constants.WS_NAMESPACE;

import com.demidrolll.myphotos.common.converter.ModelConverter;
import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.ws.ProfileWebService;
import com.demidrolll.myphotos.ws.model.ProfilePhotoSoap;
import com.demidrolll.myphotos.ws.model.ProfilePhotosSoap;
import com.demidrolll.myphotos.ws.model.ProfileSoap;
import java.util.List;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.jws.WebService;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@WebService(
    portName = "ProfileServicePort",
    serviceName = "ProfileService",
    targetNamespace = WS_NAMESPACE + "ProfileService?wsdl",
    endpointInterface = "com.demidrolll.myphotos.ws.ProfileWebService"
)
public class ProfileWebServiceBean implements ProfileWebService {

    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @EJB
    private ModelConverter modelConverter;

    @Override
    public ProfileSoap findById(Long id, boolean withPhotos, int limit) {
        Profile profile = profileService.findById(id);
        ProfileSoap result = modelConverter.convert(profile, ProfileSoap.class);
        if (withPhotos) {
            List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(limit));
            result.setPhotos(modelConverter.convertList(photos, ProfilePhotoSoap.class));
        }
        return result;
    }

    @Override
    public ProfilePhotosSoap findProfilePhotos(Long profileId, int page, int limit) {
        List<Photo> photos = photoService.findProfilePhotos(profileId, new Pageable(page, limit));
        ProfilePhotosSoap result = new ProfilePhotosSoap();
        result.setPhotos(modelConverter.convertList(photos, ProfilePhotoSoap.class));
        return result;
    }
}
