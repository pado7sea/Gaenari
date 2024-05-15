import 'package:flutter/material.dart';
import 'package:forsythia/models/inventory/my_item.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/doghouse/move_dog.dart';
import 'package:forsythia/screens/inventory/inventory_screen.dart';
import 'package:forsythia/screens/item/item_screen.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/dog_app_bar.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'dart:math';

class DogHouseScreen extends StatefulWidget {
  const DogHouseScreen({super.key});

  @override
  State<DogHouseScreen> createState() => _DogHouseScreenState();
}

class _DogHouseScreenState extends State<DogHouseScreen> {
  My my = My();
  int coin = 0;
  int affection = 0;
  String tier = "";
  String name = "";
  bool active = false;
  Random random = Random();
  int randomNumber = 0;

  @override
  void initState() {
    super.initState();
    getItem();
    loadCoin();
  }

  // List<List<String>> moveDog = [
  //   ["assets/dogs/0_1.gif", "assets/dogs/1_1.gif"],
  //   ["assets/dogs/0_2.gif", "assets/dogs/1_2.gif"],
  //   ["assets/dogs/0_3.gif", "assets/dogs/1_3.gif"],
  //   ["assets/dogs/0_4.gif", "assets/dogs/1_4.gif"],
  //   ["assets/dogs/0_5.gif", "assets/dogs/1_5.gif"],
  //   ["assets/dogs/0_6.gif", "assets/dogs/1_6.gif"],
  //   ["assets/dogs/0_7.gif", "assets/dogs/1_7.gif"],
  //   ["assets/dogs/0_8.gif", "assets/dogs/1_8.gif"],
  //   ["assets/dogs/0_9.gif", "assets/dogs/1_9.gif"],
  //   ["assets/dogs/0_10.gif", "assets/dogs/1_10.gif"],
  // ];

  // List<List<String>> actDog = [
  //   ["assets/dogs/1_0_1.gif", "assets/dogs/1_1_1.gif", "assets/dogs/1_2_1.gif", "assets/dogs/1_3_1.gif", "assets/dogs/1_4_1.gif", "assets/dogs/1_5_1.gif"],
  // ];

  getItem() async {
    MyItem myItem = await InventoryService.fetchMyItem();
    setState(() {
      my = myItem.data!;
      active = true;
    });
  }

  Future<void> loadCoin() async {
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? info = await storageService.getLoginInfo();
    setState(() {
      coin = info?.coin ?? 0;
    });
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
                      "assets/item/1.png",
                      width: MediaQuery.of(context).size.width,
                      filterQuality: FilterQuality.none,
                      fit: BoxFit.cover,
                    ),
                  ),
                  Positioned(
                    left: 0,
                    bottom: 0,
                    child: Image.asset(
                      "assets/item/2.png",
                      width: MediaQuery.of(context).size.width,
                      filterQuality: FilterQuality.none,
                      fit: BoxFit.cover,
                    ),
                  ),
                  Positioned(
                    left: 30,
                    bottom: 300,
                    child: Image.asset(
                      "assets/item/3.png",
                      width: 250,
                      filterQuality: FilterQuality.none,
                      fit: BoxFit.cover,
                    ),
                  ),
                  Positioned(
                    right: 40,
                    bottom: 200,
                    child: Image.asset(
                      "assets/item/4.png",
                      width: 100,
                      filterQuality: FilterQuality.none,
                      fit: BoxFit.cover,
                    ),
                  ),
                  Positioned(
                    left: 40,
                    bottom: 80,
                    child: Image.asset(
                      "assets/item/5.png",
                      width: 200,
                      filterQuality: FilterQuality.none,
                      fit: BoxFit.cover,
                    ),
                  ),
                  Positioned(
                    right: 40,
                    bottom: 40,
                    child: Image.asset(
                      "assets/item/6.png",
                      width: 80,
                      filterQuality: FilterQuality.none,
                      fit: BoxFit.cover,
                    ),
                  ),
                  ImageMove(id: my.pet!.id!),
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
                      .then((value) => getItem());
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
                          .then((value) => getItem());
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
