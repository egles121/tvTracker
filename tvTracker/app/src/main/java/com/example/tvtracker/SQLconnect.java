protected class SQLconnect{
    static String pathToDB = "bootcamptvtracker.cgnkkelv2t0b.us-east-2.rds.amazonaws.com";

    private static dbConnect() {
        Connection dbConnection = DriverManager.getConnection("jdbc:mysql:" + pathToDB, "admin", "Born2cess!");
    }

    private static dbCloseConnection() {

    }

    protected static getTvSeries() {

    }

}