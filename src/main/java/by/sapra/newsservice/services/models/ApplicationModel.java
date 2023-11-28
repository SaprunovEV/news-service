package by.sapra.newsservice.services.models;

public interface ApplicationModel<Data, Error> {
    Data getData();
    Error getError();

    boolean hasError();
}
