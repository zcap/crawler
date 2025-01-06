package com.ctrip.dcs.price.converter.merged;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class BankAppointment {

    private static Bank[] BANKS = new Gson().fromJson("[{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"3\",\"BranchId\":\"93\",\"BranchCode\":\"474\",\"BranchName\":\"128 皇后大道中分行\",\"BranchAddress\":\"香港中环皇后大道中128-140号威享大厦\",\"DistrictName\":\"中西区\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"17\",\"BranchId\":\"82\",\"BranchCode\":\"036\",\"BranchName\":\"上水分行\",\"BranchAddress\":\"新界上水新丰路79-81号地下\",\"DistrictName\":\"北区\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"3\",\"BranchId\":\"87\",\"BranchCode\":\"173\",\"BranchName\":\"上环分行\",\"BranchAddress\":\"香港上环德辅道中293-301号粤海投资大厦地下A铺\",\"DistrictName\":\"中西区\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"12\",\"BranchId\":\"88\",\"BranchCode\":\"559\",\"BranchName\":\"东海中心分行\",\"BranchAddress\":\"九龙尖沙咀东加连威老道98号东海商业中心地下G10-G11号铺\",\"DistrictName\":\"尖沙咀\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"3\",\"BranchId\":\"94\",\"BranchCode\":\"083\",\"BranchName\":\"交易广场分行\",\"BranchAddress\":\"香港中环交易广场1楼101及102号铺\",\"DistrictName\":\"中西区\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"15\",\"BranchId\":\"78\",\"BranchCode\":\"034\",\"BranchName\":\"元朗分行\",\"BranchAddress\":\"新界元朗青山公路150-160号元朗汇丰大厦2楼,地下及地库\",\"DistrictName\":\"屯门 / 元朗\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"9\",\"BranchId\":\"79\",\"BranchCode\":\"469\",\"BranchName\":\"又一城分行\",\"BranchAddress\":\"九龙九龙塘达之路80号又一城LG2-01\",\"DistrictName\":\"九龙城\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"5\",\"BranchId\":\"100\",\"BranchCode\":\"450\",\"BranchName\":\"太古城中心分行\",\"BranchAddress\":\"香港鲗鱼涌太古城中心第1期065号铺\",\"DistrictName\":\"东区\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"10\",\"BranchId\":\"76\",\"BranchCode\":\"472\",\"BranchName\":\"始创中心分行\",\"BranchAddress\":\"九龙旺角弥敦道750号始创中心2楼218铺\",\"DistrictName\":\"旺角 / 油麻地\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"12\",\"BranchId\":\"81\",\"BranchCode\":\"181\",\"BranchName\":\"尖沙咀分行\",\"BranchAddress\":\"九龙尖沙咀弥敦道82- 84号尖沙咀汇丰大厦地下,低层地下及地库\",\"DistrictName\":\"尖沙咀\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"15\",\"BranchId\":\"80\",\"BranchCode\":\"556\",\"BranchName\":\"屯门市广场分行\",\"BranchAddress\":\"新界屯门屯门市广场第二期高层地下1号铺\",\"DistrictName\":\"屯门 / 元朗\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"10\",\"BranchId\":\"77\",\"BranchCode\":\"127\",\"BranchName\":\"弥敦道378号分行\",\"BranchAddress\":\"九龙弥敦道378号地下B号铺\",\"DistrictName\":\"旺角 / 油麻地\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"3\",\"BranchId\":\"101\",\"BranchCode\":\"172\",\"BranchName\":\"德辅道中分行\",\"BranchAddress\":\"香港上环德辅道中141号中保集团大厦\",\"DistrictName\":\"中西区\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"13\",\"BranchId\":\"48\",\"BranchCode\":\"571\",\"BranchName\":\"愉景湾分行\",\"BranchAddress\":\"大屿山愉景湾广场C座\\\\n1楼159号舖\",\"DistrictName\":\"离岛\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"4\",\"BranchId\":\"97\",\"BranchCode\":\"499\",\"BranchName\":\"新鸿基中心分行\",\"BranchAddress\":\"香港湾仔港湾道30号新鸿基中心1楼115-117及127-133号铺\",\"DistrictName\":\"铜锣湾 / 湾仔\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"10\",\"BranchId\":\"84\",\"BranchCode\":\"001\",\"BranchName\":\"旺角分行\",\"BranchAddress\":\"九龙旺角弥敦道673号旺角汇丰大厦高层地下及地库\",\"DistrictName\":\"旺角 / 油麻地\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"4\",\"BranchId\":\"2\",\"BranchCode\":\"841\",\"BranchName\":\"柏宁分行\",\"BranchAddress\":\"香港铜锣湾告士打道310号\\\\n柏宁酒店1楼1.09 - 1.10号铺\",\"DistrictName\":\"铜锣湾 / 湾仔\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"10\",\"BranchId\":\"47\",\"BranchCode\":\"835\",\"BranchName\":\"汇丰中心分行\",\"BranchAddress\":\"九龙大角咀深旺道一号\\\\n汇丰中心高层地下E舖\",\"DistrictName\":\"旺角 / 油麻地\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"14\",\"BranchId\":\"99\",\"BranchCode\":\"804\",\"BranchName\":\"沙田分行\",\"BranchAddress\":\"新界沙田沙田中心商场第3层30D号铺\",\"DistrictName\":\"沙田 / 大埔\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"14\",\"BranchId\":\"98\",\"BranchCode\":\"557\",\"BranchName\":\"沙田广场分行\",\"BranchAddress\":\"新界沙田沙田正街21-27号沙田广场L1楼49铺\",\"DistrictName\":\"沙田 / 大埔\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"12\",\"BranchId\":\"102\",\"BranchCode\":\"408\",\"BranchName\":\"港铁香港西九龙站个人理财中心\",\"BranchAddress\":\"港铁香港西九龙站WEK G-15号铺\",\"DistrictName\":\"尖沙咀\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"4\",\"BranchId\":\"96\",\"BranchCode\":\"110\",\"BranchName\":\"熙华大厦分行\",\"BranchAddress\":\"香港湾仔轩尼诗道71-85熙华大厦地下\",\"DistrictName\":\"铜锣湾 / 湾仔\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"10\",\"BranchId\":\"92\",\"BranchCode\":\"017\",\"BranchName\":\"窝打老道分行\",\"BranchAddress\":\"九龙何文田窝打老道71号\",\"DistrictName\":\"旺角 / 油麻地\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"17\",\"BranchId\":\"90\",\"BranchCode\":\"039\",\"BranchName\":\"粉岭分行\",\"BranchAddress\":\"新界粉岭联和墟和丰街14-16号\",\"DistrictName\":\"北区\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"12\",\"BranchId\":\"91\",\"BranchCode\":\"012\",\"BranchName\":\"红磡分行\",\"BranchAddress\":\"九龙红磡马头围道37-39 号红磡商业中心地下\",\"DistrictName\":\"尖沙咀\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"11\",\"BranchId\":\"86\",\"BranchCode\":\"032\",\"BranchName\":\"荃湾分行\",\"BranchAddress\":\"新界荃湾青山公路210号富华中心1楼4A, 4B(A-C), 5及6号铺\",\"DistrictName\":\"荃湾 / 葵青\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"11\",\"BranchId\":\"83\",\"BranchCode\":\"831\",\"BranchName\":\"荃湾西分行\",\"BranchAddress\":\"新界荃湾大河道100号海之恋商场1楼1026-1037号铺\",\"DistrictName\":\"荃湾 / 葵青\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"11\",\"BranchId\":\"15\",\"BranchCode\":\"094\",\"BranchName\":\"葵芳分行\",\"BranchAddress\":\"新界葵芳新都会广场\\\\n地下153-160号铺\",\"DistrictName\":\"荃湾 / 葵青\"},{\"RowNo\":\"1\",\"AreaId\":\"1\",\"DistrictId\":\"3\",\"BranchId\":\"95\",\"BranchCode\":\"008\",\"BranchName\":\"西宝城分行\",\"BranchAddress\":\"香港西环卑路乍街8号西宝城地下低层1-3 号铺\",\"DistrictName\":\"中西区\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"7\",\"BranchId\":\"85\",\"BranchCode\":\"116\",\"BranchName\":\"观塘分行\",\"BranchAddress\":\"九龙观塘开源道71号王子大厦1楼及地下\",\"DistrictName\":\"观塘\"},{\"RowNo\":\"1\",\"AreaId\":\"3\",\"DistrictId\":\"11\",\"BranchId\":\"49\",\"BranchCode\":\"403\",\"BranchName\":\"青衣分行\",\"BranchAddress\":\"新界青衣青敬路75号\\\\n宏福花园商场地下2-5号铺\",\"DistrictName\":\"荃湾 / 葵青\"},{\"RowNo\":\"1\",\"AreaId\":\"2\",\"DistrictId\":\"12\",\"BranchId\":\"89\",\"BranchCode\":\"591\",\"BranchName\":\"黄埔花园分行\",\"BranchAddress\":\"九龙红磡黄埔花园第4期商场地下G6, 6A及G7号铺\",\"DistrictName\":\"尖沙咀\"}]", Bank[].class);
    private static List<String> APPOINTMENT_DATES = Arrays.asList("2025-01-17", "2025-01-18");

    @SneakyThrows
    public static void main(String[] args) {
        while (true) {
            System.out.println("开始检查可预约的银行...");
            List<Bank> availableBanks = Arrays.stream(BANKS).filter(bank -> available(bank, APPOINTMENT_DATES)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(availableBanks)) {
                long waitSecond = (long) (Math.random() * 40000 + 20000);
                System.out.println(String.format("没有可预约的银行, 等待：%s秒后重试", waitSecond / 1000));
                Thread.sleep(waitSecond);
                continue;
            }

            System.out.println("发现可预约的银行!");
            StringBuilder message = new StringBuilder();
            availableBanks.forEach(bank -> message.append(bank.getBranchName()).append(" (").append(bank.getDistrictName()).append(")\n"));
            JOptionPane.showMessageDialog(null, message.toString());
            break;
        }
    }

    @SneakyThrows
    private static boolean available(Bank bank, List<String> dates) {
        URL url = new URL("https://www.eticketing.hsbc.com.hk/Booking/GetDateList");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write(String.format("BranchId=%s&SegmentId=12&LangType=SC", bank.getBranchId()));
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        Result result = new Gson().fromJson(response, Result.class);
        return result.getData().stream().anyMatch(data -> dates.contains(data.getDtPreferredDate()) && !data.getAvailableTimeslotCount().equals("0"));
    }

    @Getter
    @Setter
    private static class Bank {
        private String BranchId;
        private String BranchName;
        private String DistrictName;
    }

    @Getter
    @Setter
    private static class Result {
        private List<ResultData> Data;
    }

    @Getter
    @Setter
    private static class ResultData {
        private String availableTimeslotCount;
        private String DateSC;
        private String dtPreferredDate;
    }
}