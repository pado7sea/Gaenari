import 'dart:async';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:forsythia/models/inventory/my_item.dart';
import 'package:forsythia/models/pet/pet_heart.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/inventory/inventory_screen.dart';
import 'package:forsythia/screens/item/item_screen.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/service/pet_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/dog_app_bar.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'dart:math';

class DogHouseScreen extends StatefulWidget {
  const DogHouseScreen({super.key});

  @override
  State<DogHouseScreen> createState() => _DogHouseScreenState();
}

class _DogHouseScreenState extends State<DogHouseScreen>
    with TickerProviderStateMixin {
  My my = My();
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
  int heart = 1;
  bool left = false;
  bool go = false;
  late Timer _timer;

  @override
  void initState() {
    super.initState();
    // AnimationController를 초기화하고 지속시간을 500밀리초로 설정
    _controller = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    // 초기 이미지를 설정
    getItem();
    loadCoin();
    act = random.nextInt(6);
    x = random.nextInt(6);
    y = random.nextInt(6);
    _timer = Timer.periodic(Duration(milliseconds: 3000), (timer) {
      setState(() {
        if (heart > 1) {
          heart--;
        }
      });
    });
  }

  @override
  void dispose() {
    // AnimationController를 dispose하여 메모리 누수를 방지
    _timer.cancel(); // 타이머 해제
    _controller.dispose();
    super.dispose();
  }

  getItem() async {
    MyItem myItem = await InventoryService.fetchMyItem(context);
    setState(() {
      my = myItem.data!;
      active = true;
      currentImage = "assets/dogs/1_${act}_${my.pet!.id!}.gif";
    });
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? info = await storageService.getLoginInfo();
    if (info?.myPetDto!.tier! != my.pet!.tier!) {
      // ignore: use_build_context_synchronously
      _showmodal(context);
      info?.myPetDto!.tier = my.pet!.tier!;
      storageService.saveLoginInfo(info!);
    }
  }

  postLove() async {
    if (my.pet!.affection! != 100) {
      Fluttertoast.showToast(
        msg: '${my.pet!.name}와(과) 놀아줘서 애정도가 1 올랐어요!',
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.CENTER,
        backgroundColor: myMainGreen,
      );
    }
    PetHeart petHeart = PetHeart(affection: 1, id: my.pet!.id);
    await PetService.fetchPetLove(petHeart);
    setState(() {
      go = false;
      my.pet!.affection = my.pet!.affection! + 1;
    });
    if (my.pet!.affection! == 100) {
      getItem();
    }
  }

  List<String> tiers = [
    'BRONZE',
    'SILVER',
    'GOLD',
    'PLATINUM',
    'DIAMOND',
    'MASTER',
  ];

  void _showmodal(BuildContext parentContext) {
    showDialog(
      context: parentContext,
      barrierDismissible: false,
      builder: (BuildContext context) {
        // dialogContext를 여기서 사용해서 나중에 다시 뽑기 버튼에서 사용해.
        return Stack(
          children: [
            Dialog(
              backgroundColor: myBackground,
              insetPadding: EdgeInsets.fromLTRB(50, 170, 50, 170),
              child: Container(
                height: 550,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(25)),
                ),
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.all(10.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          SizedBox(width: 10),
                          InkWell(
                            onTap: () {
                              Navigator.pop(context);
                            },
                            child: Image.asset(
                              'assets/icons/common_close.png',
                              width: 20.0,
                              height: 20.0,
                            ),
                          ),
                          TextButton(
                            onPressed: () {
                              Navigator.pop(context);
                            },
                            child: Text16(
                              text: "닫기",
                              bold: true,
                            ),
                          ),
                        ],
                      ),
                    ),
                    Center(
                      child: Column(
                        children: [
                          Text25(
                            text: "티어 상승!",
                            bold: true,
                          ),
                          SizedBox(height: 20),
                          Image.asset(
                            "assets/dog_tier/tier_${my.pet!.tier!}.png",
                            width: 120,
                            fit: BoxFit.contain,
                            filterQuality: FilterQuality.none,
                          ),
                          SizedBox(height: 20),
                          Text16(
                            text: "애정을 듬뿍 주어서",
                            bold: true,
                          ),
                          Text16(
                            text: "${my.pet!.tier!}로 티어가 올랐어요",
                            bold: true,
                          ),
                          SizedBox(height: 30),
                          SmallButton(
                            text: "확인",
                            onPressed: () {
                              Navigator.pop(context);
                            },
                            widthPadding: 100,
                            active: true,
                          )
                        ],
                      ),
                    )
                  ],
                ),
              ),
            )
          ],
        );
      },
    );
  }

  Future<void> loadCoin() async {
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? info = await storageService.getLoginInfo();
    setState(() {
      coin = info?.coin ?? 0;
    });
  }

  int findIdByCategory(List<Items>? items, String category) {
    for (var item in items!) {
      if (item.category == category) {
        return item.id!;
      }
    }
    // 카테고리를 찾지 못했을 때 예외처리
    throw Exception('카테고리를 찾을 수 없습니다: $category');
  }

  void moveImage() {
    setState(() {
      if (heart < 10) {
        heart++;
      } else if (heart == 10) {
        go = true;
        heart = 1;
      }
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

    // AnimationController를 이용하여 위치 변경 애니메이션을 실행
    _controller.reset();
    _controller.forward().then((_) {
      setState(() {
        act = random.nextInt(6);
        currentImage = "assets/dogs/1_${act}_${my.pet!.id!}.gif";
      });
    });
    if (go) {
      postLove();
    }
  }

  @override
  Widget build(BuildContext context) {
    randomNumber = random.nextInt(6);
    return active
        ? Scaffold(
            appBar: DogAppBar(
              tier: my.pet!.tier,
              name: my.pet!.name,
              affection: my.pet!.affection,
              coinvalue: coin,
            ),
            body: SizedBox(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height,
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
                  GestureDetector(
                    // GestureDetector를 사용하여 이미지를 감싼다
                    onTap: () {
                      if (!go) {
                        moveImage();
                      }
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
                                Row(
                                  children: [
                                    Image.asset(
                                      "assets/color_icons/heart_$heart.png",
                                      width: 80,
                                      filterQuality: FilterQuality.none,
                                      fit: BoxFit.cover,
                                    )
                                  ],
                                )
                              ],
                            )),
                      ],
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(10.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        SizedBox(height: 10),
                        _togglebutton(),
                      ],
                    ),
                  ),
                ],
              ),
            ))
        : Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          );
  }

  Widget _togglebutton() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          decoration: myBoxDecoration,
          child: Padding(
            padding: const EdgeInsets.all(10.0),
            child: GestureDetector(
                onTap: () {
                  Navigator.of(context)
                      .push(SlidePageRoute(nextPage: InventoryScreen()))
                      .then((result) {
                    // 돌아올 때 데이터를 수신하고 처리
                    if (result == "update") {
                      // 리스트 업데이트 메서드 호출
                      getItem();
                      loadCoin();
                    } else {
                      getItem();
                      loadCoin();
                    }
                  });
                },
                child: Row(
                  mainAxisSize:
                      MainAxisSize.min, // Row의 가로 길이를 내부 요소만큼만 차지하도록 설정
                  children: const [
                    Image(
                      image: AssetImage('assets/icons/doghouse_drawer.png'),
                      width: 30,
                      height: 30,
                      fit: BoxFit.cover,
                      filterQuality: FilterQuality.none,
                    ),
                    SizedBox(width: 5),
                    Text16(text: '보관함')
                  ],
                )),
          ),
        ),
        SizedBox(height: 15),
        Container(
            decoration: myBoxDecoration,
            child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: GestureDetector(
                    onTap: () {
                      Navigator.of(context)
                          .push(SlidePageRoute(nextPage: ItemScreen()))
                          .then((result) {
                        // 돌아올 때 데이터를 수신하고 처리
                        if (result == "update") {
                          // 리스트 업데이트 메서드 호출
                          getItem();
                          loadCoin();
                          print("sddsfsdf");
                        }
                      });
                    },
                    child: Row(
                      mainAxisSize:
                          MainAxisSize.min, // Row의 가로 길이를 내부 요소만큼만 차지하도록 설정
                      children: const [
                        Image(
                          image: AssetImage('assets/icons/doghouse_random.png'),
                          width: 30,
                          height: 30,
                          fit: BoxFit.cover,
                          filterQuality: FilterQuality.none,
                        ),
                        SizedBox(width: 5),
                        Text16(text: '뽑기')
                      ],
                    )))),
      ],
    );
  }
}
