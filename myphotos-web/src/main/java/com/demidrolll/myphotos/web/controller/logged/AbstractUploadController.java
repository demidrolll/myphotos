package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.web.model.PartImageResource;
import com.demidrolll.myphotos.web.security.SecurityUtils;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.demidrolll.myphotos.common.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;

/**
 * Use docs.fineuploader.com service
 *
 * @param <T>
 */
public abstract class AbstractUploadController<T> extends HttpServlet {

    @Inject
    private Logger logger;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("qqfile");
        Profile profile = SecurityUtils.getCurrentProfile();
        final AsyncContext asyncContext = req.startAsync(req, resp);
        asyncContext.setTimeout(DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS);
        uploadImage(profile, new PartImageResource(part), new AsyncOperation<T>() {

            @Override
            public long getTimeOutInMillis() {
                return asyncContext.getTimeout();
            }

            @Override
            public void onSuccess(T result) {
                try {
                    handleSuccess(asyncContext, result);
                } finally {
                    asyncContext.complete();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                try {
                    handleFailed(asyncContext, throwable);
                } finally {
                    asyncContext.complete();
                }

            }
        });
    }

    private void handleFailed(AsyncContext asyncContext, Throwable throwable) {
        logger.log(Level.SEVERE, throwable, () -> "Async operation failed: " + throwable.getMessage());
        JsonObject json = Json.createObjectBuilder()
                .add("success", false)
                .build();
        sendJsonResponse(json, asyncContext);
    }

    private void handleSuccess(AsyncContext asyncContext, T result) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("success", true);
        getResultMap(result, (HttpServletRequest) asyncContext.getRequest()).forEach(jsonObjectBuilder::add);
        JsonObject json = jsonObjectBuilder.build();
        sendJsonResponse(json, asyncContext);
    }

    private void sendJsonResponse(JsonObject json, AsyncContext asyncContext) {
        try {
            HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            RoutingUtils.sendFileUploaderJson(json, request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "sendJsonResponse failed: " + e.getMessage(), e);
        }
    }

    protected abstract Map<String, String> getResultMap(T result, HttpServletRequest request);

    protected abstract void uploadImage(Profile profile, PartImageResource partImageResource, AsyncOperation<T> asyncOperation);
}
