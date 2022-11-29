module com.example.cloudstorageclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.transport;
    requires io.netty.buffer;
    requires io.netty.common;
    requires com.google.gson;


    opens com.example.cloudstorageclient to javafx.fxml;
    exports com.example.cloudstorageclient;
}