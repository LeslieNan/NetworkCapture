package com.leslienan.wireshark_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.WiresharkUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val model = NetLogModel(
                "GET",
                "https://api.222jjjisdjfisjofsifnobbbbbaawqqqqqqrrrrrr.com", "id=100", "",
                "", "responseHeader",
                "{\"msg\":\"\",\"code\":100000,\"data\":{\"abnormalMoney\":0.0,\"afterSalePhone\":\"17548967852\",\"areaName\":\"瓯海区\",\"cancelMoney\":0.0,\"cancelState\":\"not_cancel\",\"cancelTimeString\":\"\",\"changeNumber\":\"\",\"fabnormalIntegralToUse\":0.0,\"faddressdetail\":\"梧田街\",\"faddressremark\":\"\",\"famount\":99.0,\"famountpiece\":99.0,\"famountprice\":593.94,\"fbasamount\":593.94,\"fboxmodel\":\"1\",\"fchildorderid\":\"CGRA202303092368684024\",\"fchildpaymenttime\":\"2023-03-09 16:31:01\",\"fchildpaymenttimeString\":\"2023-03-09 16:31:01\",\"fcity\":\"温州市\",\"fcodeprovince\":\"浙江省温州市瓯海区梧田街道\",\"fconsignee\":\"新桥\",\"fconsumetime\":\"2023-03-21 00:00:00\",\"fcontactway\":\"15555555555\",\"fcreatetime\":\"2023-03-09 16:30:55\",\"fcreatetimeString\":\"2023-03-09 16:30:55\",\"fcreatorid\":\"26056345-9646-4c6b-beb7-e9e7cd59c7b4\",\"fdeductintegral\":0.0,\"fdeletestatus\":0.0,\"fdelivery\":\"2023-03-21 00:00:00\",\"fdeliveryString\":\"2023-03-21 00:00:00\",\"fdiscountamount\":0.0,\"fdiscountamounts\":0.0,\"fexcpstatus\":1.0,\"fflutetype\":\"8\",\"fflutetypeString\":\"A\",\"fgiveintegral\":0.0,\"fgokeyarea\":3301.0,\"fgroupareaid\":\"330304002\",\"fgroupgoodid\":\"a8eb1757-9748-4551-bc25-09cb376a1ded\",\"fgroupgoodname\":\"lyh123-限购\",\"fgroupprice\":6.0,\"fhformula\":0.0,\"fhlineformula\":\"\",\"fhstaveexp\":\"\",\"fid\":\"CGRA202303092368684024\",\"fileUrl\":\"dev/fileserver/save/img/8f/4e9c30050fa5436ba1a4afa72573863f.jpg\",\"fintegralToUse\":0.0,\"fintegralToUseTotal\":0.0,\"fkeyarea\":3303.0,\"flabel\":\"张晓东\",\"flayer\":3.0,\"flnglat\":\"120.679977,27.964493\",\"flogistics\":\"123\",\"fmanufacturer\":\"400\",\"fmarktingplanid\":\"4aba81fb-26ef-4978-a727-9f19c545538a\",\"fmateriafid\":\"123\",\"fmaterialfid\":\"123\",\"fmateriallength\":99.0,\"fmateriallengthplus\":0.0,\"fmaterialname\":\"123\",\"fmaterialwidth\":99.0,\"fmaterialwidthplus\":2.0,\"fmktplanchangeid\":\"6e422dd4-b48a-4cae-bebb-37bdc845cef4\",\"fnewtype\":\"\",\"fnumber\":\"CGRA202303092368684024\",\"foperatetime\":\"2023-03-09 16:30:55\",\"foperatetimeString\":\"2023-03-09 16:30:55\",\"foperator\":\"\",\"foperatorid\":\"26056345-9646-4c6b-beb7-e9e7cd59c7b4\",\"forderId\":\"MGRA202303092368624420\",\"fordertime\":\"2023-03-09 16:30:55\",\"fordertimeString\":\"2023-03-09 16:30:55\",\"fordertype\":1.0,\"fpaymenttime\":\"2023-03-09 16:31:00\",\"fpaymenttimeString\":\"2023-03-09 16:31:00\",\"fpaytype\":0.0,\"fpostpone\":0.0,\"fproductarea\":98.99,\"fproductionno\":\"B4002303090007\",\"fprovince\":\"浙江省\",\"fpuserid\":\"26056345-9646-4c6b-beb7-e9e7cd59c7b4\",\"fpusername\":\"张晓东\",\"frowid\":0.0,\"fseries\":\"连做\",\"fstate\":3.0,\"fstatus\":3.0,\"fstavetype\":\"净边不压线\",\"fsumdiscountamount\":0.0,\"ftilemodelid\":\"单A瓦\",\"ftransportsheet\":\"\",\"fullGiveActivity\":true,\"fullGiveActivityId\":\"1632937958085893633\",\"funitprice\":6.0,\"fuserCouponId\":\"-1\",\"fvformula\":0.0,\"fvlineformula\":\"\",\"fvstaveexp\":\"\",\"id\":\"193039720667263681\",\"inStock\":0.0,\"isAfterSalesDetails\":0.0,\"isApplyAfterSale\":1.0,\"isSign\":0.0,\"isUrgeOrder\":1.0,\"manufacturerName\":\"温州东诚包装有限公司\",\"newDisplay\":false,\"openingNumber\":0.0,\"optimalGateWidth\":0.0,\"originalOrderState\":0.0,\"priceChangeType\":1.0,\"pricingPlanGenre\":0.0,\"productCount\":0.0,\"productType\":1.0,\"progressDescription\":\"生产编号B4002303090007\",\"progressDescriptionFlag\":false,\"repeat\":0.0,\"returnlog\":0.0,\"schemeId\":\"\",\"total\":0.0,\"townName\":\"梧田街道\"},\"success\":true}\n",
                300, System.currentTimeMillis()
            )
            WiresharkUtil.addNetLog(model)
        }
    }
}