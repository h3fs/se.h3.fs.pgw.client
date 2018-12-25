package se.h3.fs.pgw.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.h3.fs.pgw.api.ModuleInfo;
import se.h3.fs.pgw.api.PaymentRequest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class RetroFitTest {

    public static final String BASE_URL = "http://192.168.1.100:8080/";

    IH3FSPGWClient service;

    Map<String, String> headers = new HashMap<>();

    @Before
    public void setUp() throws Exception {

        OkHttpClient.Builder hcb = new OkHttpClient.Builder();

        hcb.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder rb = original.newBuilder();

            for (String key : headers.keySet()) {
                rb.addHeader(key, headers.get(key));
            }

            Request request = rb
                    .method(original.method(), original.body())
                    .build();

            okhttp3.Response response = chain.proceed(request);

            if (!response.isSuccessful()) {
                System.out.println("HOLD!");
            }

            return response;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(hcb.build())
                .build();

        service = retrofit.create(IH3FSPGWClient.class);
    }


    /**
     * @throws Exception
     */

    @Test
    public void testGetToken() throws Exception {

        ModuleInfo[] mis = service.getModules().execute().body();

        assert mis != null;

        for (ModuleInfo mi : mis) {
            System.out.println(mi.getPath());
        }


        final String module = mis[3].getPath();

        Files.copy(service.logo(module, "png").execute().body().byteStream(), Paths.get("/tmp/logo.png"), StandardCopyOption.REPLACE_EXISTING);

        PaymentRequest req = service.create(module, 100.00f, "SEK").execute().body();
        System.out.println(req.getState());

        Files.copy(service.qr(module, req.getId(), "png").execute().body().byteStream(), Paths.get("/tmp/qr.png"), StandardCopyOption.REPLACE_EXISTING);

        PaymentRequest req2 = service.waitfor(module, req.getId()).execute().body();
        System.out.println(req2.getState());




/*
        Session s = new Session();
        s.password = "538436";
        s.username = "nordictechhouse";


        Response<Token> response = service.getToken("password", "nordictechhouse", "538436").execute();

        assert response.body() != null;
        System.out.println(response.body().access_token);

        headers.put("Authorization", "Bearer " + response.body().access_token);

        Response<ProductGroup> execute = service.getAllProducts().execute();

        for (Product p : execute.body().products) {
            System.out.println(p.description);
            System.out.println(p.price);
        }

        for (Outlet o : service.getOutlets().execute().body()) {
            System.out.println(o.id);
            System.out.println(o.name);

            MonthalySale ms = service.getMonthalySale(o.id, 2018, 12).execute().body();

            System.out.println(ms.GrandTotal);
            System.out.println(ms.NetTotal);

            for(XCategory hs :  ms.Categories) {
                System.out.println(hs.GroupName);
                System.out.println(hs.SaleHour);
                System.out.println(hs.GrossTotal);
                System.out.println(hs.NetTotal);
            }
*/
/*
            for(OutletSale os :  ms.OutletSales) {
                System.out.println(os.GroupName);
                System.out.println(os.SaleHour);
                System.out.println(os.GrossTotal);
                System.out.println(os.NetTotal);
            }
*/
    }

//    class Actor {
//
//        IKartiera service;
//        Map<String, String> headers = new HashMap<>();
//
//        Session s;
//
//        public Actor() {
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//            httpClient.addInterceptor(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
//                    Request original = chain.request();
//
//                    Request.Builder rb = original.newBuilder();
//
//                    for (String key : headers.keySet()) {
//                        rb.addHeader(key, headers.get(key));
//                    }
//
//                    Request request = rb
//                            .method(original.method(), original.body())
//                            .build();
//
//                    okhttp3.Response response = chain.proceed(request);
//
//                    if (!response.isSuccessful()) {
//                        System.out.println("HOLD!");
//                    }
//
//                    return response;
//                }
//
//
//            });
//
//            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create())
//                    .baseUrl(BASE_URL)
//                    .client(httpClient.build())
//                    .build();
//
//            service = retrofit.create(IKartiera.class);
//        }
//
//        void login(String username, String password) throws IOException {
//            s = new Session();
//            s.username = username;
//            s.password = password;
//
//            Response<Token> ex = service.postSession(s).execute();
//
//            Assert.assertTrue(ex.isSuccessful());
//
//            headers.put("authorization", "Bearer " + ex.body().token);
//            headers.put("x-program", ex.body().claims.environment);
//
//            System.out.println(ex.body().token);
//        }
//
//        LockedTransfer2[] getLockedTransfers() throws IOException {
//            Response<LockedTransfer2[]> ex = service.getLockedTransfers().execute();
//            Assert.assertTrue(ex.isSuccessful());
//
//            return ex.body();
//        }
//
//        Purse[] getPurses() throws IOException {
//            Response<Purse[]> ex = service.getPurses().execute();
//            Assert.assertTrue(ex.isSuccessful());
//
//            return ex.body();
//        }
//
//        String lockTransfer(String amount, String password, Purse purse, int timeout, String receiverUsername) throws IOException {
//
//            LockedTransfer lt = new LockedTransfer();
//            lt.amount = amount;
//            lt.password = password;
//            lt.purse = purse;
//            lt.receiverUsername = receiverUsername;
//            lt.senderUsername = s.username;
//            lt.timeout = timeout;
//
//            Response<String> ex = service.postCreateLockedTransfer(lt).execute();
//            Assert.assertTrue(ex.isSuccessful());
//
//            return ex.body();
//        }
//
//        void lockRelease(String transferId, String password) throws IOException {
//
//            LockRelease lr = new LockRelease();
//            lr.transferId = transferId;
//            lr.password = password;
//
//            Response<String> ex = service.postReleaseLock(lr).execute();
////            Assert.assertTrue(ex.isSuccessful());
//        }
//
//        WalletBalance getWalletBalance() throws IOException {
//            Response<WalletBalance> ex = service.getWalletBalance().execute();
//            Assert.assertTrue(ex.isSuccessful());
//
//            return ex.body();
//        }
//
//        void setWalletBalance(String cur) throws IOException {
//            PrimaryCurrency pc = new PrimaryCurrency();
//            pc.primaryCurrency = cur;
//
//            Response<Void> ex = service.postSetWalletPrimaryCurrency(pc).execute();
//            Assert.assertTrue(ex.isSuccessful());
//        }
//
//        Notification[] getNotifications() throws IOException {
//            Response<Notification[]> ex = service.getNotifications().execute();
//            Assert.assertTrue(ex.isSuccessful());
//
//            return ex.body();
//        }
//
//        public void postPurce(String currency) throws IOException {
//            PurceDescriptor pd = new PurceDescriptor();
//            pd.currency = currency;
//
//            Response<Void> ex = service.postPurses(pd).execute();
//            Assert.assertTrue(ex.isSuccessful());
//        }
//
//        public void putWalletPrimaryCurrency(String currency) throws IOException {
//            PrimaryCurrency pc = new PrimaryCurrency();
//            pc.primaryCurrency = currency;
//
//            Response<Void> ex = service.putPrimaryCurrency(pc).execute();
//            Assert.assertTrue(ex.isSuccessful());
//        }
//    }
//
//    @Test
//    public void testPurces() throws Exception {
//
//        Actor alice = new Actor();
//        alice.login("aliceUser001", "Password1234!");
//
//        Actor bob = new Actor();
//        bob.login("bobUser001", "Password1234!");
//
////        for(Purse purse :alice.getPurses()) {
////            System.out.println(purse.identifier);
////        }
//
//        for (Purse purse : bob.getPurses()) {
//            System.out.println(purse.identifier);
//        }
//
//        bob.putWalletPrimaryCurrency("USD");
//
//        WalletBalance wb = bob.getWalletBalance();
//
//        System.out.println(wb.availableBalance);
//
//
////        bob.postPurce("USD");
////        bob.postPurce("EUR");
//
//
//        for (Notification n : bob.getNotifications()) {
//            System.out.println(n.subject);
//            System.out.println(n.description);
//            System.out.println(n.username);
//            System.out.println(n.notificationId);
//        }
//
//        for (Notification n : alice.getNotifications()) {
//            System.out.println(n.subject);
//            System.out.println(n.description);
//            System.out.println(n.username);
//            System.out.println(n.notificationId);
//        }
//
//
//    }
//
//    @Test
//    public void testBob2() throws Exception {
//        Actor bob = new Actor();
//        bob.login("bobUser001", "Password1234!");
//
//        System.out.println(bob.getWalletBalance().availableBalance);
//        System.out.println("Hello");
//
//    }
//
//    @Test
//    public void testAlice() throws Exception {
//        Actor alice = new Actor();
//        alice.login("aliceUser001", "Password1234!");
//
//        System.out.println(alice.getWalletBalance().availableBalance);
//        System.out.println("Hello");
//
//    }
//
//    @Test
//    public void testTransfer() throws Exception {
//
//        Actor alice = new Actor();
//        alice.login("aliceUser001", "Password1234!");
//
//        Actor bob = new Actor();
//        bob.login("bobUser001", "Password1234!");
//
//        Purse purse = alice.getPurses()[0];
//
//        String ltpass = "TestPass";
//
//        String lockTransfer = alice.lockTransfer("1", ltpass, purse, 1000000, bob.s.username);
//
//        System.out.println(lockTransfer);
//
//        for (LockedTransfer2 lt2 : bob.getLockedTransfers()) {
////            bob.lockRelease(lt2.transferId,ltpass);
//
//            System.out.println(lt2.transferId);
//            System.out.println(lt2.dateTimeInitialized);
//
//            SimpleDateFormat format = new SimpleDateFormat(
//                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
//            format.setTimeZone(TimeZone.getTimeZone("UTC"));
//            Date date = format.parse(lt2.dateTimeInitialized);
//            Date now = new Date();
//
//            System.out.println(date);
//            System.out.println(now);
//
//            if (now.before(date)) {
//                bob.lockRelease(lt2.transferId, ltpass);
//            }
//
//        }
//
//        for (LockedTransfer2 lt2 : bob.getLockedTransfers()) {
//            System.out.println(lt2.transferId);
//        }
//
//        System.out.println(alice.getWalletBalance().availableBalance);
//        System.out.println(bob.getWalletBalance().availableBalance);
//    }

}