module ca.cmpt213.asn5 {
    requires transitive javafx.controls;
    requires com.google.gson;

    exports ca.cmpt213.asn5;
    opens ca.cmpt213.asn5 to com.google.gson;
}
