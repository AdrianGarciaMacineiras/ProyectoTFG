import NetworkError from "./NetworkError";
import { config } from "../config/constants.js";

let networkErrorCallback;

const isJson = (response) => {
  const contentType = response.headers.get("content-type");
  return contentType && contentType.indexOf("application/json") !== -1;
};

const handleOkResponse = (response, onSuccess) => {
  if (!response.ok) {
    return false;
  }
  if (!onSuccess) {
    return true;
  }
  if (response.status === 204) {
    onSuccess();
    return true;
  }
  if (isJson(response)) {
    response.json().then((payload) => onSuccess(payload));
  } else {
    response.blob().then((blob) => onSuccess(blob));
  }
  return true;
};

const handle4xxResponse = (response, onErrors) => {
  if (response.status < 400 || response.status >= 500) {
    return false;
  }
  if (!isJson(response)) {
    throw new NetworkError();
  }
  if (onErrors) {
    response.json().then((payload) => {
      if (payload.globalError || payload.fieldErrors) {
        onErrors(payload);
      }
    });
  }
  return true;
};

const handleResponse = (response, onSuccess, onErrors) => {
  if (handleOkResponse(response, onSuccess)) {
    return;
  }
  if (handle4xxResponse(response, onErrors)) {
    return;
  }
  throw new NetworkError();

export const appFetch = (path, options, onSuccess, onErrors) =>
  fetch(`${config.BASE_PATH}${path}`, options)
    .then((response) => handleResponse(response, onSuccess, onErrors))
    .catch(networkErrorCallback);