package homework;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import homework.entity.*;
import homework.jdbc.util.DriverManagerConnectionMethod;
import homework.jdbc.util.HikariConnectionMethod;
import homework.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static homework.jdbc.JdbcCrud.*;

/**
 * Test
 *
 * @author qrXun on 2020/12/2
 */
public class Test {

    private static final ExecutorService insertPool = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2,
            1000, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(1000000),
            new ThreadFactoryBuilder().setNameFormat("qrxun-pool-%d").build());

    public static void main(String[] args) {
//        initBasicInfo();
        // 获取商品信息
        GoodsInfoRepository goodsInfoRepository = new GoodsInfoRepository(new HikariConnectionMethod());
        List<GoodsInfo> goodsInfoList = goodsInfoRepository.findAll();

        // 获取用户信息
        UserInfoRepository userInfoRepository = new UserInfoRepository(new HikariConnectionMethod());
        List<UserInfo> userInfoList = userInfoRepository.findAll();

        // 获取收件人信息
        ReceiverInfoRepository receiverInfoRepository = new ReceiverInfoRepository(new HikariConnectionMethod());
        List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAll();

        OrderMainInfoRepository orderMainInfoRepository = new OrderMainInfoRepository(
                new DriverManagerConnectionMethod(JDBC_URL, USER_NAME, PASSWORD)
        );
        OrderDetailRepository orderDetailRepository = new OrderDetailRepository(
                new DriverManagerConnectionMethod(JDBC_URL, USER_NAME, PASSWORD)
        );

        OrderMainInfoRepository poolMainRepository = new OrderMainInfoRepository(
                new HikariConnectionMethod()
        );
        OrderDetailRepository poolDetailRepository = new OrderDetailRepository(
                new HikariConnectionMethod()
        );


        // 1. 单条插入，原生 jdbc 方式
//        saveDataOneByOne(goodsInfoList, receiverInfoList, userInfoList, orderMainInfoRepository, orderDetailRepository);

        // 2. 单条插入，使用线程池
        saveDataOneByOne(goodsInfoList, receiverInfoList, userInfoList, poolMainRepository, poolDetailRepository);

        // 3. 通过 addBatch 一次性新增，原生 jdbc 方式
//        saveDataBatch(goodsInfoList, receiverInfoList, userInfoList, orderMainInfoRepository, orderDetailRepository);

        // 4. 通过 addBatch 一次性新增，线程池方式
//        saveDataBatch(goodsInfoList, receiverInfoList, userInfoList, poolMainRepository, poolDetailRepository);


    }


    /**
     * 单条数据插入
     * @param goodsInfoList
     * @param receiverInfoList
     * @param userInfoList
     * @param orderMainInfoRepository
     * @param orderDetailRepository
     */
    private static void saveDataOneByOne(List<GoodsInfo> goodsInfoList,
                                         List<ReceiverInfo> receiverInfoList,
                                         List<UserInfo> userInfoList,
                                         OrderMainInfoRepository orderMainInfoRepository,
                                         OrderDetailRepository orderDetailRepository) {
        Random random = new Random();
        int goodsCount = goodsInfoList.size();
        int userCount = userInfoList.size();
        int receiverCount = receiverInfoList.size();
        Long nowTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        for (int i = 0; i < 1000000; i++) {
            insertPool.execute(() -> {
                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                GoodsInfo goodsInfo = goodsInfoList.get(random.nextInt(goodsCount));
                // 商品详情
                OrderDetail orderDetail = OrderDetail.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsNum(1)
                        .price(goodsInfo.getDiscountPrice())
                        .totalPrice(goodsInfo.getDiscountPrice())
                        .build();
                OrderMainInfo orderMainInfo = OrderMainInfo.builder()
                        .uniqueId(UUID.randomUUID().toString().replaceAll("-", ""))
                        .insertTime(now)
                        .updateTime(now)
                        .orderStatus(random.nextInt(4))
                        .totalPrice(orderDetail.getTotalPrice())
                        .leaveMessage("商品质量很好")
                        .transportPrice(new BigDecimal("5.11"))
                        .receiverInfoId(receiverInfoList.get(random.nextInt(receiverCount)).getId())
                        .buyerId(userInfoList.get(random.nextInt(userCount)).getUniqueId())
                        .build();
                orderDetail.setOrderId(orderMainInfo.getUniqueId());
                orderMainInfoRepository.insertOne(orderMainInfo);
                orderDetailRepository.insertOne(orderDetail);
            });

        }
        try {
            insertPool.shutdown();
            insertPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long endTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("--------------耗时" + (endTimeStamp - nowTimeStamp) + "秒-------------------");
    }

    private static void saveDataBatch(List<GoodsInfo> goodsInfoList,
                                      List<ReceiverInfo> receiverInfoList,
                                      List<UserInfo> userInfoList,
                                      OrderMainInfoRepository orderMainInfoRepository,
                                      OrderDetailRepository orderDetailRepository){
        Random random = new Random();
        int goodsCount = goodsInfoList.size();
        int userCount = userInfoList.size();
        int receiverCount = receiverInfoList.size();
        List<OrderMainInfo> mainInfoList = new ArrayList<>();
        List<OrderDetail> detailList = new ArrayList<>();
        for (int i = 0; i< 1000000;i++){
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            GoodsInfo goodsInfo = goodsInfoList.get(random.nextInt(goodsCount));
            // 商品详情
            OrderDetail orderDetail = OrderDetail.builder()
                    .goodsId(goodsInfo.getId())
                    .goodsNum(1)
                    .price(goodsInfo.getDiscountPrice())
                    .totalPrice(goodsInfo.getDiscountPrice())
                    .build();
            OrderMainInfo orderMainInfo = OrderMainInfo.builder()
                    .uniqueId(UUID.randomUUID().toString().replaceAll("-", ""))
                    .insertTime(now)
                    .updateTime(now)
                    .orderStatus(random.nextInt(4))
                    .totalPrice(orderDetail.getTotalPrice())
                    .leaveMessage("商品质量很好")
                    .transportPrice(new BigDecimal("5.11"))
                    .receiverInfoId(receiverInfoList.get(random.nextInt(receiverCount)).getId())
                    .buyerId(userInfoList.get(random.nextInt(userCount)).getUniqueId())
                    .build();
            orderDetail.setOrderId(orderMainInfo.getUniqueId());
            mainInfoList.add(orderMainInfo);
            detailList.add(orderDetail);
        }
        Long nowTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        orderDetailRepository.insertAll(detailList);
        orderMainInfoRepository.insertAll(mainInfoList);
        Long endTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("--------------耗时" + (endTimeStamp - nowTimeStamp) + "秒-------------------");
    }



    /**
     * 初始化部分初始数据
     */
    private static void initBasicInfo() {
        // 商品类型
        GoodsTypeRepository goodsTypeRepository = new GoodsTypeRepository(new HikariConnectionMethod());
        Integer[] categoryIdArray = {0, 1, 2, 3};
        String[] nameArray = {"数码", "家具", "食品", "化妆品"};
        for (int i = 0; i < 4; i++) {
            GoodsType goodsType = GoodsType.builder()
                    .categoryId(categoryIdArray[i])
                    .name(nameArray[i])
                    .build();
            goodsTypeRepository.insertOne(goodsType);
        }
        // 商品信息
        GoodsInfoRepository goodsInfoRepository = new GoodsInfoRepository(new HikariConnectionMethod());
        String[] goodName = {"sony相机", "顾家沙发", "康师傅方便面", "花王护手霜"};
        BigDecimal[] originalPrice = {
                new BigDecimal("21999.9"),
                new BigDecimal("19999.9"),
                new BigDecimal("5.5"),
                new BigDecimal("20.0")
        };
        BigDecimal[] discountPrice = {
                new BigDecimal("19999.9"),
                new BigDecimal("15888.8"),
                new BigDecimal("3.5"),
                new BigDecimal("15.5")
        };
        String[] description = {"sony最新相机", "顾家沙发", "康师傅红烧牛绒面", "花王护手霜"};
        Integer[] stockArray = {2000000, 2000000, 20000000, 2000000};
        for (int i = 0; i < 4; i++) {
            GoodsInfo goodsInfo = GoodsInfo.builder()
                    .name(goodName[i])
                    .typeId(categoryIdArray[i])
                    .title(goodName[i])
                    .discountPrice(discountPrice[i])
                    .normalPrice(originalPrice[i])
                    .description(description[i])
                    .stock(stockArray[i])
                    .build();
            goodsInfoRepository.insertOne(goodsInfo);
        }
        // 用户信息
        UserInfoRepository userInfoRepository = new UserInfoRepository(new HikariConnectionMethod());
        String[] name = {"张三", "李四", "王五"};
        String[] account = {"zhangsan", "lisi", "wagnwu"};
        String[] phoneNumber = {"18111111111", "18222222222", "18333333333"};
        String[] password = {"123456", "123456789", "1234567890"};
        String[] birthday = {"1991-01-01", "1992-01-01", "1993-01-01"};
        for (int i = 0; i < 3; i++) {
            UserInfo userInfo = UserInfo.builder()
                    .uniqueId(UUID.randomUUID().toString().replaceAll("-", ""))
                    .name(name[i])
                    .account(account[i])
                    .phoneNumber(phoneNumber[i])
                    .password(password[i])
                    .insertTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .updateTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .birthday(birthday[i])
                    .build();
            userInfoRepository.insertOne(userInfo);
        }
        // 收货地址
        ReceiverInfoRepository receiverInfoRepository = new ReceiverInfoRepository(new HikariConnectionMethod());
        String[] address = {"北京市xx区xx街道xx幢", "杭州市xx区xx街道xx幢", "上海市xx区xx街道xx幢"};
        int[] postlcode = {100000, 310000, 200000};
        for (int i = 0; i < 3; i++) {
            ReceiverInfo receiverInfo = ReceiverInfo.builder()
                    .name(name[i])
                    .address(address[i])
                    .phoneNumber(phoneNumber[i])
                    .postalcode(postlcode[i])
                    .build();
            receiverInfoRepository.insertOne(receiverInfo);

        }
    }

}
