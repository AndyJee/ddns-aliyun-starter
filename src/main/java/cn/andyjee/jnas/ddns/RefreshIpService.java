package cn.andyjee.jnas.ddns;

import cn.andyjee.jnas.ddns.util.IpUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author AndyJee
 */
public class RefreshIpService implements Runnable {

    private final IAcsClient iAcsClient;

    private final String domainName;

    public RefreshIpService(IAcsClient iAcsClient, String domainName) {
        this.iAcsClient = iAcsClient;
        this.domainName = domainName;
    }

    @Override
    public void run() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("开始刷新IP" + now);

        // 查询指定二级域名的最新解析记录
        DescribeDomainRecordsResponse acsResponse;
        try {
            DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
            describeDomainRecordsRequest.setDomainName(domainName);
            describeDomainRecordsRequest.setRRKeyWord("@");
            describeDomainRecordsRequest.setType("A");
            acsResponse = iAcsClient.getAcsResponse(describeDomainRecordsRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //读取解析记录
        List<DescribeDomainRecordsResponse.Record> domainRecords = acsResponse.getDomainRecords();
        if (domainRecords == null || domainRecords.isEmpty()) {
            return;
        }

        //刷新IP
        DescribeDomainRecordsResponse.Record record = domainRecords.get(0);
        String recordId = record.getRecordId();
        String recordsValue = record.getValue();
        String currentHostIp = IpUtil.getCurrentIpByNetCn();

        if (!currentHostIp.equals(recordsValue)) {

            System.out.println("需要修改IP记录：" + currentHostIp + ", 当前时间" + now);

            // 修改解析记录
            try {
                UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
                updateDomainRecordRequest.setRR("@");
                updateDomainRecordRequest.setRecordId(recordId);
                updateDomainRecordRequest.setValue(currentHostIp);
                updateDomainRecordRequest.setType("A");

                //无视更新结果
                iAcsClient.getAcsResponse(updateDomainRecordRequest);
            } catch (ClientException e) {
                e.printStackTrace();
            }

        }

    }

}
