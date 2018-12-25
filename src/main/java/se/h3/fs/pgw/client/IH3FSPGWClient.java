package se.h3.fs.pgw.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import se.h3.fs.pgw.api.ModuleInfo;
import se.h3.fs.pgw.api.PaymentRequest;

public interface IH3FSPGWClient {

    @GET("info/modules")
    Call<ModuleInfo[]> getModules();

    @GET("{module}/logo.{ext}")
    Call<ResponseBody> logo(
            @Path("module") String module,
            @Path("ext") String ext);

    @FormUrlEncoded
    @POST("{module}/create")
    Call<PaymentRequest> create(
            @Path("module") String module,
            @Field("amount") double amount,
            @Field("currency") String currency,
            @Field("ref") String ref);

    @FormUrlEncoded
    @POST("{module}/create")
    Call<PaymentRequest> create(
            @Path("module") String module,
            @Field("amount") double amount,
            @Field("currency") String currency);

    @GET("{module}/{id}/waitfor")
    Call<PaymentRequest> waitfor(
            @Path("module") String module,
            @Path("id") Long id);

    @GET("{module}/{id}/waitfor")
    Call<PaymentRequest> waitfor(
            @Path("module") String module,
            @Path("id") Long id,
            @Query("timeout") long timeout);

    @GET("{module}/{id}/status")
    Call<PaymentRequest> status(
            @Path("module") String module,
            @Path("id") Long id);

    @PUT("{module}/{id}/cancel")
    Call<PaymentRequest> cancel(
            @Path("module") String module,
            @Path("id") Long id);

    @GET("{module}/{id}/qr.{ext}")
    Call<ResponseBody> qr(
            @Path("module") String module,
            @Path("id") Long id,
            @Path("ext") String ext);

    @GET("{module}/{id}/qr.{ext}")
    Call<ResponseBody> qr(
            @Path("module") String module,
            @Path("id") Long id,
            @Path("ext") String ext,
            @Query("size") long size);


    /*
    @FormUrlEncoded
    @POST("Token")
    Call<Token> getToken(
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password);


    @GET("api/Product/GetAllProducts")
    Call<ProductGroup> getAllProducts();

    @GET("api/Sale/GetOutlets")
    Call<Outlet[]> getOutlets();


    @GET("api/Sale/GetMonthalySale")
    Call<MonthalySale> getMonthalySale(@Query("outletId") String outletId,
                                       @Query("year") int year,
                                       @Query("month") int month);


 @PostMapping("create")
    public PaymentRequest create(@RequestParam("amount") double amount,
                                 @RequestParam("currency") String currency,
                                 @RequestParam(value = "ref", required = false) String ref) {

        PaymentRequest request = new PaymentRequest(amount, currency, ref);
        onPaymentRequested(request);
        requests.put(request.getId(), request);
        return request;
    }

    @GetMapping("{id}/waitfor")
    public PaymentRequest waitfor(@PathVariable("id") Long id,
                                  @RequestParam(value = "timeout", required = false, defaultValue = "60000") long timeout) {
        PaymentRequest request = requests.get(id);
        request.waitForEndState(timeout);
        return request;
    }

    @PutMapping("{id}/cancel")
    public PaymentRequest cancel(@PathVariable("id") Long id) {
        PaymentRequest request = requests.get(id);
        onPaymentRequestCanceled(request);
        return request;
    }

    @GetMapping("{id}/status")
    public PaymentRequest status(@PathVariable("id") Long id) {
        return requests.get(id);
    }

    @GetMapping("{id}/qr.{ext}")
    public void qr(HttpServletResponse response,
                   @PathVariable("id") Long id,
                   @PathVariable("ext") String ext,
                   @RequestParam(value = "size", required = false, defaultValue = "350") int size)
            throws IOException, WriterException {
        PaymentRequest req = requests.get(id);
        response.setContentType("image/" + ext);
        QRCodeGenerator.generateQRCodeImage(req.getQr(), size, size, response.getOutputStream(), ext);
    }



GET api/Sale/GetMonthalySale?outletId={outletId}&year={year}&month={month}



        @POST("session")
        Call<Token> postSession(@Body Session session);

        @POST("users/verify")
        Call<Void> verifyEmail(@Query("email") String email);

        @PUT("users/verify")
        Call<Void> verifyEmail(@Body Verification verification);

        @PUT("/wallet")
        Call<Void> putPrimaryCurrency(@Body PrimaryCurrency pc);

        @GET("wallet")
        Call<WalletBalance> getWalletBalance();

        @GET("wallet/purses/balances")
        Call<PurseBalance[]> getPurceBalances();

        @GET("wallet/purses")
        Call<Purse[]> getPurses();

        @POST("wallet/purses")
        Call<Void> postPurses(@Body PurceDescriptor pd);

        @GET("terms")
        Call<Terms> getTerms();

        @GET("users/exists/username/{username}")
        Call<Boolean> getUserExists(@Path("username") String username);

        @GET("users/exists/email/{email}")
        Call<Boolean> getEmailExists(@Path("email") String email);

        @GET("wallet/transfer/locked")
        Call<LockedTransfer2[]> getLockedTransfers();

        @POST("users")
        Call<Void> postCreateNewUser(@Body UserRegistration ur);

        @POST("send-accounts")
        Call<Void> postCreateNewSendingAccount(@Body SendingAccount sa);

        @POST("wallet/transfer/locked")
        Call<String> postCreateLockedTransfer(@Body LockedTransfer lt);

        @POST("wallet/transfer/locked/release")
        Call<String> postReleaseLock(@Body LockRelease lr);

        @GET("wallet/supported-currencies")
        Call<Currency[]> getSupportedCurrencies();

        @POST("wallet")
        Call<Void> postSetWalletPrimaryCurrency(@Body PrimaryCurrency pc);

        @GET("notifications")
        Call<Notification[]> getNotifications();




*/
/*
        @POST("users/verify")
        Call<Void> postVerifyEmail(@Query("email") String email);
*//*


     */
/*
        @GET("wallet/purses/{id}")
        Call<WalletBalance> getBalance(@Path("id") String id);

        @GET("wallet/purses/{id}/transactions")
        Call<Transaction[]> getPurseTransactions(@Path("id") String id);

        @GET("wallet/transactions")
        Call<Transaction[]> getTransactions(@Query("start") String start, @Query("end") String end);

        @GET("send-accounts")
        Call<Contact[]> getSendAccounts();

        @POST("send-accounts")
        Call<Void> postSendAccounts(@Body Contact contact);

        @PUT("send-accounts")
        Call<Void> putSendAccounts(@Body Contact contact);

        @DELETE("send-accounts")
        Call<Void> deleteSendAccounts(@Body Contact contact);

        @GET("wallet/supported-currencies")
        Call<Currency[]> getSupportedCurrencies();

        @PUT("users/terms")
        Call<Terms> putTerms(@Body TermsAcceptence terms);


*//*





//        @GET("sics")
//        Call<SecureImageContainer[]> getAll();
//
//        @PUT("sic")
//        Call<Integer> put(@Body SecureImageContainer sic);
//
//        @PUT("location")
//        Call<Void> put(@Body Location location);
//
//        @GET("locations")
//        Call<Location[]> getLocations();
*/
}
