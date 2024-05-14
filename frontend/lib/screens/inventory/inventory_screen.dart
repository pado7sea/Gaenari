// ignore_for_file: use_build_context_synchronously

import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:forsythia/models/inventory/item_info.dart';
import 'package:forsythia/models/inventory/pet_list.dart';
import 'package:forsythia/models/inventory/set_list.dart';
import 'package:forsythia/models/pet/pet_adopt.dart';
import 'package:forsythia/models/pet/pet_res.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/service/pet_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/large_app_bar.dart';
import 'package:forsythia/widgets/modal.dart';
import 'package:provider/provider.dart';
import 'inventory_info.dart';

class InventoryScreen extends StatefulWidget {
  const InventoryScreen({super.key});

  @override
  State<InventoryScreen> createState() => _InventoryScreenState();
}

class _InventoryScreenState extends State<InventoryScreen> {
  TextEditingController _petnamecontroller = TextEditingController();
  List<Set> set = [];
  List<Pet> pet = [];
  bool active = false;
  bool item = true;
  int coin = 0;
  @override
  void initState() {
    super.initState();
    _getSet();
    loadCoin();
  }

  _getSet() async {
    SetList setList = await InventoryService.fetchSetList();
    PetList petList = await InventoryService.fetchPetList();
    setState(() {
      set = setList.data!;
      pet = petList.data!;
      active = true;
    });
  }

  Future<void> loadCoin() async {
    // SecureStorageService를 사용해서 로그인 정보를 불러오고, 닉네임을 설정해줘
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? info = await storageService.getLoginInfo();
    setState(() {
      coin = info?.coin ?? 0; // 만약 닉네임이 없으면 빈 문자열로 설정해줘
    });
  }

  List<String> images = [
    'assets/gif/shepherd_standandlook.gif',
    'assets/gif/grayhound_standandlook.gif',
    'assets/gif/husky_standandlook.gif',
    'assets/gif/pomeranian1_standandlook.gif',
    'assets/gif/pomeranian2_standandlook.gif',
    'assets/gif/shiba_standandlook.gif',
    'assets/gif/pug_standandlook.gif',
    'assets/gif/retriever1_standandlook.gif',
    'assets/gif/retriever2_standandlook.gif',
    'assets/gif/wolf_standandlook.gif',
  ];

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
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(
        title: '보관함',
        sentence: '강아지 집을 꾸며보자!',
        coin: true,
        coinvalue: coin,
      ),
      body: active
          ? Column(
              children: [
                _togglebutton(),
                Expanded(
                  child: ListView(
                    shrinkWrap: true,
                    children: [
                      Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: item
                            ? Column(
                                children: [
                                  _buildRowOfItems(0),
                                  SizedBox(height: 16),
                                  _buildRowOfItems(2),
                                  SizedBox(height: 16),
                                  _buildRowOfItems(4),
                                  SizedBox(height: 16),
                                  _buildRowOfItems(6),
                                  SizedBox(height: 16),
                                ],
                              )
                            : Column(
                                children: [
                                  _buildRowOfDogs(0),
                                  SizedBox(height: 16),
                                  _buildRowOfDogs(2),
                                  SizedBox(height: 16),
                                  _buildRowOfDogs(4),
                                  SizedBox(height: 16),
                                  _buildRowOfDogs(6),
                                  SizedBox(height: 16),
                                  _buildRowOfDogs(8),
                                  SizedBox(height: 16),
                                ],
                              ),
                      ),
                    ],
                  ),
                ),
              ],
            )
          : Center(
              child: CircularProgressIndicator(),
            ),
    );
  }

  Widget _togglebutton() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(20, 20, 20, 10),
      child: Row(
        children: [
          GestureDetector(
            onTap: () {
              setState(() {
                item = true;
              });
            },
            child: Row(
              children: [
                Image(
                  image: AssetImage('assets/emoji/drawer.png'),
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
                SizedBox(width: 10),
                Text20(
                  text: '아이템',
                  bold: true,
                  textColor: item ? myBlack : myGrey,
                ),
              ],
            ),
          ),
          SizedBox(width: 10),
          Container(
            width: 2,
            height: 20,
            decoration: BoxDecoration(color: myGrey),
          ),
          SizedBox(width: 10),
          GestureDetector(
            onTap: () {
              setState(() {
                item = false;
              });
            },
            child: Row(
              children: [
                Image(
                  image: AssetImage('assets/emoji/dogface.png'),
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
                SizedBox(width: 10),
                Text20(
                  text: '강아지',
                  bold: true,
                  textColor: item ? myGrey : myBlack,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildRowOfItems(int startIndex) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly, // 아이템을 가운데 정렬
      children: [
        Flexible(flex: 1, child: _buildItemTile(info[startIndex], startIndex)),
        SizedBox(width: 16),
        Flexible(
            flex: 1,
            child: _buildItemTile(info[startIndex + 1], startIndex + 1)),
      ],
    );
  }

  Widget _buildItemTile(ItemInfo itemInfo, int index) {
    return InkWell(
      onTap: () {
        // 각 아이템을 눌렀을 때의 동작 추가 가능
      },
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            decoration: myBorderBoxDecoration,
            height: 160,
          ),
          SizedBox(height: 8.0), // 아이템 이름과 세트 이름 사이 간격 조정
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text16(
                text: "  ${itemInfo.setName!}",
                bold: true,
              ),
              Text16(
                text: "${set[index].itemCnt.toString()}/6  ",
                textColor: myGrey,
              )
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildRowOfDogs(int startIndex) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly, // 아이템을 가운데 정렬
      children: [
        Flexible(
            flex: 1, child: _buildDogTile(dogbreeds[startIndex], startIndex)),
        SizedBox(width: 16),
        Flexible(
            flex: 1,
            child: _buildDogTile(dogbreeds[startIndex + 1], startIndex + 1)),
      ],
    );
  }

  Widget _buildDogTile(String itemInfo, int index) {
    return InkWell(
      onTap: () {
        !pet[index].isHave! && coin >= 20000
            ? showModalBottomSheet(
                isScrollControlled: true,
                context: context,
                builder: (BuildContext context) {
                  return BackdropFilter(
                    filter: ImageFilter.blur(sigmaX: 3, sigmaY: 3), // 블러 효과 설정
                    child: ModalContent(
                      height: 650,
                      customWidget: Column(
                        children: [
                          Image.asset(
                            images[index],
                            width: 100,
                            height: 100,
                            filterQuality: FilterQuality.none,
                            fit: BoxFit.fitHeight,
                          ),
                          SizedBox(height: 20),
                          Text16(text: itemInfo, bold: true),
                          // Text16(text: ' 새로 입양하시겠어요?', bold: true),
                          SizedBox(height: 20),
                          _name(),
                          GestureDetector(
                            onTap: () async {
                              SecureStorageService storageService =
                                  SecureStorageService();
                              LoginInfo? info =
                                  await storageService.getLoginInfo();
                              PetAdopt petAdopt = PetAdopt(
                                  id: index + 1, name: _petnamecontroller.text);
                              PetRes petRes =
                                  await PetService.fetchPetAdopt(petAdopt);
                              print(petRes.message);
                              setState(() {
                                pet[index].isHave = true;
                                pet[index].pets?.affection = 0;
                                pet[index].pets?.name = petAdopt.name;
                                pet[index].pets?.tier = "BRONZE";
                                _petnamecontroller = TextEditingController();
                              });
                              SecureStorageService secureStorageService =
                                  SecureStorageService();
                              info?.coin = (info.coin! - 20000);
                              secureStorageService.saveLoginInfo(info!);
                              loadCoin();
                              Navigator.of(context).pop();
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(16),
                              child: Container(
                                decoration: _petnamecontroller.text != ""
                                    ? myActiveBoxDecoration
                                    : myNoneBoxDecoration,
                                padding: EdgeInsets.all(16),
                                height: 55,
                                child: Center(
                                  child: Text16(
                                    text: "입양",
                                    bold: true,
                                    textColor: _petnamecontroller.text != ""
                                        ? myBlack
                                        : myGrey,
                                  ),
                                ),
                              ),
                            ),
                          ),
                          SizedBox(height: 10),
                          Text12(
                              text: "이름을 한번 정한 후 수정이 불가합니다.",
                              textColor: myGrey),
                        ],
                      ),
                    ),
                  );
                },
              )
            : !pet[index].pets!.isPartner!
                ? showModalBottomSheet(
                    context: context,
                    builder: (BuildContext context) {
                      return BackdropFilter(
                        filter:
                            ImageFilter.blur(sigmaX: 3, sigmaY: 3), // 블러 효과 설정
                        child: ModalContent(
                          height: 300,
                          customWidget: Column(
                            children: [
                              SizedBox(height: 20),
                              Text16(text: '$itemInfo를', bold: true),
                              Text16(text: ' 새로 입양하시겠어요?', bold: true),
                              SizedBox(height: 20),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  SmallButton(
                                    onPressed: () {
                                      Navigator.of(context).pop();
                                    },
                                    text: "취소",
                                    active: false,
                                    widthPadding: 50,
                                  ),
                                  SizedBox(
                                    width: 16,
                                  ),
                                  SmallButton(
                                    onPressed: () {
                                      // _dogAdd(index);
                                    },
                                    text: "입양",
                                    active: true,
                                    widthPadding: 50,
                                  ),
                                ],
                              ),
                              SizedBox(height: 10),
                              Text12(
                                  text: "친구신청 후 요청취소가 불가합니다.",
                                  textColor: myGrey),
                            ],
                          ),
                        ),
                      );
                    },
                  )
                : print("dd");
      },
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            decoration: pet[index].pets!.isPartner!
                ? myYellowBoxDecoration
                : pet[index].isHave!
                    ? myActiveBoxDecoration
                    : myBorderBoxDecoration,
            height: 180,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.all(5.0),
                  child: Text16(
                      text: pet[index].pets!.isPartner!
                          ? "파트너"
                          : pet[index].isHave!
                              ? "보유중"
                              : "미보유"),
                ),
                Image.asset(
                  images[index],
                  width: double.infinity,
                  filterQuality: FilterQuality.none,
                  fit: BoxFit.cover,
                ),
              ],
            ),
          ),
          SizedBox(height: 8.0), // 아이템 이름과 세트 이름 사이 간격 조정
          Column(
            children: [
              Text16(
                text: itemInfo,
                bold: true,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text16(
                    text: pet[index].isHave!
                        ? "${pet[index].pets!.name}"
                        : "${pet[index].pets!.price}",
                    textColor: pet[index].isHave! ? myBlack : myGrey,
                  ),
                  SizedBox(
                    width: 5,
                  ),
                  !pet[index].isHave!
                      ? Image.asset(
                          "assets/color_icons/icon_coin.png",
                          width: 20,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        )
                      : Image.asset(
                          "assets/dog_tier/tier_${pet[index].pets!.tier}.png",
                          width: 20,
                          filterQuality: FilterQuality.none,
                          fit: BoxFit.cover,
                        )
                ],
              )
            ],
          ),
        ],
      ),
    );
  }

  // 인풋
  Widget _name() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
      child: Column(
        children: [
          Row(
            children: const [
              Image(
                image: AssetImage('assets/emoji/pensil.png'),
                width: 20,
                height: 20,
                fit: BoxFit.cover,
              ),
              Text16(text: '  이름')
            ],
          ),
          SizedBox(height: 10),
          TextField(
            decoration: InputDecoration(
                hintText: '강아지의 이름을 입력해주세요.',
                hintStyle: TextStyle(color: Colors.grey),
                // tap 시 borderline 색상 지정
                focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: myBlack))),
            controller: _petnamecontroller,
            // inputFormatters: [
            //   FilteringTextInputFormatter.allow(RegExp(
            //       "[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣!@#<>?\":_`~;[\\]\\|=+)(*&^%£€.{}'`-s/]")),
            // ],
            maxLength: 8,
          ),
        ],
      ),
    );
  }
}
