import 'package:flutter/material.dart';
import 'package:forsythia/models/inventory/mate_home.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/doghouse/move_dog.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'dart:math';

import 'package:forsythia/widgets/small_app_bar.dart';

class MateDogHouseScreen extends StatefulWidget {
  final int memberId;
  final String memberName;

  const MateDogHouseScreen(
      {super.key, required this.memberId, required this.memberName});

  @override
  State<MateDogHouseScreen> createState() => _MateDogHouseScreenState();
}

class _MateDogHouseScreenState extends State<MateDogHouseScreen>
    with TickerProviderStateMixin {
  Home my = Home();
  int coin = 0;
  int affection = 0;
  String tier = "";
  String name = "";
  bool active = false;
  Random random = Random();
  int randomNumber = 0;
  // AnimationController를 생성
  late AnimationController _controller;

  // 현재 보여지는 이미지의 경로를 저장하기 위한 변수
  late String currentImage;
  // 이미지가 움직이는 중인지 여부를 나타내는 변수
  List<double> dx = [10, 50, 100, 150, 200, 250];
  List<double> dy = [110, 140, 210, 280, 350, 370];
  int x = 0;
  int y = 0;
  int act = 0;
  bool left = false;
  int myDogId = 0;
  String myDogName = "";
  String myDogTier = "";
  @override
  void initState() {
    super.initState();
    // AnimationController를 초기화하고 지속시간을 500밀리초로 설정
    _controller = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    // 초기 이미지를 설정
    getMydog();
    getItem();
    act = random.nextInt(6);
    x = random.nextInt(6);
    y = random.nextInt(6);
  }

  getMydog() async {
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? loginInfo = await storageService.getLoginInfo();
    setState(() {
      myDogId = loginInfo!.myPetDto!.id!;
      myDogName = loginInfo.myPetDto!.name!;
      myDogTier = loginInfo.myPetDto!.tier!;
    });
  }

  List<String> dogbreeds = [
    '셰퍼드',
    '그레이하운드',
    '허스키',
    '갈색 포메라니안',
    '흰색 포메라니안',
    '시바',
    '퍼그',
    '갈색 리트리버',
    '흰색 리트리버',
    '늑대',
  ];

  @override
  void dispose() {
    // AnimationController를 dispose하여 메모리 누수를 방지
    _controller.dispose();
    super.dispose();
  }

  getItem() async {
    MateHome myItem =
        await InventoryService.fetchMateHome(context, widget.memberId);
    setState(() {
      my = myItem.data!;
      active = true;
      currentImage = "assets/dogs/1_${act}_${my.pet!.id!}.gif";
    });
  }

  int findIdByCategory(List<Items>? items, String category) {
    for (var item in items!) {
      if (item.category == category) {
        return item.id!;
      }
    }

    throw Exception('카테고리를 찾을 수 없습니다: $category');
  }

  List<String> images = [
    'assets/item_tier/tier_r.png',
    'assets/item_tier/tier_e.png',
    'assets/item_tier/tier_l.png',
  ];
  List<String> tiers = [
    'BRONZE',
    'SILVER',
    'GOLD',
    'PLATINUM',
    'DIAMOND',
    'MASTER',
  ];
  int? findItemTierById(int itemId) {
    if (itemId <= 30) {
      return 0;
    } else if (itemId < 42) {
      return 1;
    } else {
      return 2;
    }
  }

  void moveImage() {
    setState(() {
      int a = x;
      int b = y;
      x = random.nextInt(6);
      y = random.nextInt(6);
      if ((a - x).abs() + (b - y).abs() > 4 || (b - y).abs() > 2) {
        currentImage = "assets/dogs/1_${my.pet!.id!}.gif";
      } else {
        currentImage = "assets/dogs/0_${my.pet!.id!}.gif";
      }
      left = (a - x) < 0 ? false : true;
    });

    _controller.reset();
    _controller.forward().then((_) {
      setState(() {
        act = random.nextInt(6);
        currentImage = "assets/dogs/1_${act}_${my.pet!.id!}.gif";
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    randomNumber = random.nextInt(6);
    return active
        ? Scaffold(
            appBar: SmallAppBar(
              title: "${widget.memberName}의 집",
              back: true,
            ),
            body: Column(
              children: [
                SizedBox(
                  width: MediaQuery.of(context).size.width,
                  height: MediaQuery.of(context).size.height - 200,
                  child: Stack(
                    children: [
                      Positioned(
                        left: 0,
                        top: 0,
                        child: Image.asset(
                          "assets/item/${findIdByCategory(my.items, "Wall")}.png",
                          width: MediaQuery.of(context).size.width,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        ),
                      ),
                      Positioned(
                        left: 0,
                        bottom: 0,
                        child: Image.asset(
                          "assets/item/${findIdByCategory(my.items, "Floor")}.png",
                          width: MediaQuery.of(context).size.width,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        ),
                      ),
                      Positioned(
                        left: 30,
                        bottom: 320,
                        child: Image.asset(
                          "assets/item/${findIdByCategory(my.items, "Rug")}.png",
                          width: 350,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        ),
                      ),
                      Positioned(
                        right: 60,
                        bottom: 60,
                        child: Image.asset(
                          "assets/item/${findIdByCategory(my.items, "Bowl")}.png",
                          width: 70,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        ),
                      ),
                      Positioned(
                        left: 40,
                        bottom: 120,
                        child: Image.asset(
                          "assets/item/${findIdByCategory(my.items, "Cushion")}.png",
                          width: 200,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        ),
                      ),
                      Positioned(
                        right: 40,
                        bottom: 200,
                        child: Image.asset(
                          "assets/item/${findIdByCategory(my.items, "Toy")}.png",
                          width: 100,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        ),
                      ),
                      ImageMove(
                        id: myDogId,
                        tier: myDogTier,
                        name: myDogName,
                        myhome: false,
                      ),
                      GestureDetector(
                        // GestureDetector를 사용하여 이미지를 감싼다
                        onTap: () {
                          moveImage();
                        },
                        child: Stack(
                          children: <Widget>[
                            AnimatedPositioned(
                              left: dx[x],
                              top: dy[y],
                              duration: Duration(milliseconds: 500),
                              child: Column(
                                children: [
                                  left
                                      ? Transform.flip(
                                          flipX: true,
                                          child: Image.asset(
                                            currentImage,
                                            width: 200.0 - (50 - y * 10),
                                            fit: BoxFit.cover,
                                            filterQuality: FilterQuality.none,
                                          ),
                                        )
                                      : Image.asset(
                                          currentImage,
                                          width: 200.0 - (50 - y * 10),
                                          fit: BoxFit.cover,
                                          filterQuality: FilterQuality.none,
                                        ),
                                  SizedBox(height: 5),
                                  Row(
                                    children: [
                                      Image.asset(
                                        "assets/dog_tier/tier_${my.pet!.tier}.png",
                                        width: 18,
                                        filterQuality: FilterQuality.none,
                                      ),
                                      SizedBox(width: 3),
                                      Text16(
                                        text: "${my.pet!.name}",
                                        bold: true,
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                SizedBox(
                    height: 96,
                    child: Container(
                      color: myTier[tiers.indexOf(my.pet!.tier!)],
                      padding: EdgeInsets.symmetric(horizontal: 20),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Row(
                            children: [
                              Image.asset(
                                "assets/dog_tier/tier_${my.pet!.tier!}.png",
                                width: 50,
                                fit: BoxFit.contain,
                                filterQuality: FilterQuality.none,
                              ),
                              SizedBox(width: 5),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Text20(
                                    text: ' ${my.pet!.name!}',
                                    bold: true,
                                  ),
                                ],
                              ),
                            ],
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.end,
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Text16(
                                  text: "${widget.memberName}님의 아이템 티어 ",
                                  bold: true),
                              SizedBox(height: 3),
                              Row(
                                children: [
                                  Image.asset(
                                    images[findItemTierById(my.items![0].id!)!],
                                    width: 30,
                                    fit: BoxFit.contain,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(
                                    width: 3,
                                  ),
                                  Image.asset(
                                    images[findItemTierById(my.items![1].id!)!],
                                    width: 30,
                                    fit: BoxFit.contain,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(
                                    width: 3,
                                  ),
                                  Image.asset(
                                    images[findItemTierById(my.items![2].id!)!],
                                    width: 30,
                                    fit: BoxFit.contain,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(
                                    width: 3,
                                  ),
                                  Image.asset(
                                    images[findItemTierById(my.items![3].id!)!],
                                    width: 30,
                                    fit: BoxFit.contain,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(
                                    width: 3,
                                  ),
                                  Image.asset(
                                    images[findItemTierById(my.items![4].id!)!],
                                    width: 30,
                                    fit: BoxFit.contain,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(
                                    width: 3,
                                  ),
                                  Image.asset(
                                    images[findItemTierById(my.items![5].id!)!],
                                    width: 30,
                                    fit: BoxFit.contain,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(
                                    width: 3,
                                  ),
                                ],
                              ),
                            ],
                          )
                        ],
                      ),
                    )),
              ],
            ))
        : Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          );
  }
}
