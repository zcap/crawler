package com.ctrip.dcs.data.center.application.processor.handler;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

class BOCBankAppointment {

    @SneakyThrows
    public static void main(String[] args) {
        List<String> dates = Arrays.asList("20250221");
        List<String> districts = Arrays.asList("油尖旺区", "中西区", "深水埗区");
        while (true) {
            System.out.println("开始检查可预约的银行...");

            boolean match = false;
            try {
                match = availableDistrict(districts) && availableDate(dates);
            } catch (Exception e) {
                System.out.println("检查可预约的银行失败: " + e.getMessage());
            }
            if (!match) {
                long waitSecond = (long) (Math.random() * 10000 + 1000);
                System.out.println(String.format("没有可预约的银行, 等待：%s秒后重试", waitSecond / 1000));
                Thread.sleep(waitSecond);
                continue;
            }
            displayTray(dates, districts);
            System.out.println("发现可预约的银行!");
            break;
        }
    }

    @SneakyThrows
    private static boolean availableDate(List<String> dates) {
        URL url = new URL("https://transaction.bochk.com/whk/form/openAccount/jsonAvailableDateAndTime.action");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("bean.appDate=");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        DateResult dateResult = new Gson().fromJson(response, DateResult.class);
        return dates.stream().map(d -> dateResult.getDateQuota().get(d)).anyMatch("A"::equals);
    }

    @SneakyThrows
    private static boolean availableDistrict(List<String> districts) {
        URL url = new URL("https://transaction.bochk.com/whk/form/openAccount/jsonBrAvailableDT.action");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("bean.appDate=&bean.branchCode=");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        DistrictResult districtResult = new Gson().fromJson(response, DistrictResult.class);
        return districtResult.getBranchDistrictList().stream()
                .filter(branchDistrict -> Objects.nonNull(branchDistrict.getMessageCn()))
                .filter(branchDistrict -> districts.contains(branchDistrict.getMessageCn()))
                .anyMatch(branchDistrict -> branchDistrict.getValue().endsWith("_A"));
    }

    public static void displayTray(List<String> dates, List<String> districts) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "发现可以预约的中银");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("发现可以预约的中银");
        tray.add(trayIcon);
        trayIcon.displayMessage("发现可以预约的中银", String.format("日期：%s，地区：%s", new Gson().toJson(dates), new Gson().toJson(districts)), TrayIcon.MessageType.INFO);
    }

    @Getter
    @Setter
    private static class DistrictResult {
        private List<BranchDistrict> branchDistrictList;
    }

    @Getter
    @Setter
    private static class BranchDistrict {
        private String messageCn;
        private String value;
    }

    @Getter
    @Setter
    private static class DateResult {
        private Map<String, String> dateQuota;
    }

}
