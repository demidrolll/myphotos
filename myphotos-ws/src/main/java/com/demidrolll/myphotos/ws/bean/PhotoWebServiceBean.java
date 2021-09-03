package com.demidrolll.myphotos.ws.bean;

import static com.demidrolll.myphotos.model.SortMode.POPULAR_AUTHOR;
import static com.demidrolll.myphotos.model.SortMode.POPULAR_PHOTO;
import static com.demidrolll.myphotos.ws.Constants.WS_NAMESPACE;

import com.demidrolll.myphotos.common.converter.ModelConverter;
import com.demidrolll.myphotos.common.converter.UrlConverter;
import com.demidrolll.myphotos.model.OriginalImage;
import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.SortMode;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.ws.PhotoWebService;
import com.demidrolll.myphotos.ws.model.ImageLinkSoap;
import com.demidrolll.myphotos.ws.model.PhotoSoap;
import com.demidrolll.myphotos.ws.model.PhotosSoap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

@MTOM
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@WebService(
    portName = "PhotoServicePort",
    serviceName = "PhotoService",
    targetNamespace = WS_NAMESPACE + "PhotoService?wsdl",
    endpointInterface = "com.demidrolll.myphotos.ws.PhotoWebService"
)
public class PhotoWebServiceBean implements PhotoWebService {

    @EJB
    private PhotoService photoService;

    @Inject
    private ModelConverter modelConverter;

    @Inject
    private UrlConverter urlConverter;

    @Override
    public PhotosSoap findAllOrderByPhotoPopularity(int page, int limit, boolean withTotal) {
        return findAll(POPULAR_PHOTO, page, limit, withTotal);
    }

    @Override
    public PhotosSoap findAllOrderByAuthorPopularity(int page, int limit, boolean withTotal) {
        return findAll(POPULAR_AUTHOR, page, limit, withTotal);
    }

    private PhotosSoap findAll(SortMode mode, int page, int limit, boolean withTotal) {
        List<Photo> photos = photoService.findPopularPhotos(mode, new Pageable(page, limit));
        PhotosSoap result = new PhotosSoap();
        result.setPhotos(modelConverter.convertList(photos, PhotoSoap.class));
        if (withTotal) {
            result.setTotal(photoService.countAllPhotos());
        }
        return result;
    }

    @Override
    public ImageLinkSoap viewLargePhoto(Long photoId) {
        String relativeUrl = photoService.viewLargePhoto(photoId);
        String absoluteUrl = urlConverter.convert(relativeUrl);
        return new ImageLinkSoap(absoluteUrl);
    }

    @Override
    public DataHandler downloadOriginalImage(Long photoId) {
        OriginalImage image = photoService.downloadOriginalImage(photoId);
        return new DataHandler(new OriginalImageDataSource(image));
    }

    private static class OriginalImageDataSource implements DataSource {

        private final OriginalImage originalImage;

        public OriginalImageDataSource(OriginalImage image) {
            originalImage = image;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return originalImage.getIn();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException("OutputStream is not supported");
        }

        @Override
        public String getContentType() {
            return "image/jpeg";
        }

        @Override
        public String getName() {
            return originalImage.getName();
        }
    }
}
