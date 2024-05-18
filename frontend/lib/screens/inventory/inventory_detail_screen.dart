import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:forsythia/models/inventory/item_list.dart';
import 'package:forsythia/screens/inventory/inventory_info.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/modal.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class InventoryDetailScreen extends StatefulWidget {
  final int setId;

  const InventoryDetailScreen({super.key, required this.setId});

  @override
  State<InventoryDetailScreen> createState() => _InventoryDetailScreenState();
}

class _InventoryDetailScreenState extends State<InventoryDetailScreen> {
  late Set setDetail = Set();

  @override
  void initState() {
    super.initState();
    getDetail();
  }

  getDetail() async {
    ItemList response =
        await InventoryService.fetchItemList(context, widget.setId + 1);
    setState(() {
      setDetail = response.data!;
    });
  }

  int? findItemTierById(int itemId) {
    if (itemId <= 30) {
      return 0;
    } else if (itemId <= 42) {
      return 1;
    } else {
      return 2;
    }
  }

  List<String> images = [
    'assets/item_tier/tier_r.png',
    'assets/item_tier/tier_e.png',
    'assets/item_tier/tier_l.png',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '${info[widget.setId].setName}',
        back: true,
      ),
      body: setDetail.setId != null
          ? SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(
                      height: 10,
                    ),
                    Container(
                      decoration: myBorderBoxDecoration,
                      margin: EdgeInsets.symmetric(horizontal: 8),
                      child: ClipRRect(
                          borderRadius: BorderRadius.all(Radius.circular(16)),
                          child: Image.asset(
                            "assets/item/set_${setDetail.setId!}.png",
                            width: double.infinity,
                            fit: BoxFit.cover,
                            filterQuality: FilterQuality.none,
                          )),
                    ),
                    SizedBox(height: 20),
                    SizedBox(
                      width: double.infinity,
                      height: 350,
                      child: GridView.count(
                        physics: NeverScrollableScrollPhysics(),
                        crossAxisCount: 3, // 열의 개수
                        childAspectRatio: 2 / 3,
                        children: List.generate(6, (index) {
                          bool isHave = setDetail.items![index].isHave!;
                          bool isEquip = setDetail.items![index].isEquip!;
                          int id = setDetail.items![index].id!;
                          return GestureDetector(
                            onTap: () {
                              if (!isEquip && isHave) {
                                showModalBottomSheet(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return modal(index, id);
                                  },
                                );
                              }
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Stack(
                                children: [
                                  Padding(
                                      padding: const EdgeInsets.all(8),
                                      child: Opacity(
                                        opacity: isHave ? 1 : 0.2,
                                        child: Container(
                                          decoration: isEquip
                                              ? myBlackBorderBoxDecoration
                                              : myBoxDecoration,
                                          child: ClipRRect(
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(12)),
                                            child: Image.asset(
                                              "assets/item/$id.png",
                                              width: double.infinity,
                                              height: 90,
                                              fit: BoxFit.fitWidth,
                                            ),
                                          ),
                                        ),
                                      )),
                                  Image.asset(
                                    images[findItemTierById(id)!],
                                    width: 30,
                                    height: 30,
                                    fit: BoxFit.cover,
                                  ),
                                  Positioned(
                                      left: 0,
                                      top: 110,
                                      child: Text16(
                                        text: info[setDetail.setId! - 1]
                                            .item![index]
                                            .itemName!,
                                        bold: true,
                                      )),
                                  Positioned(
                                      right: 0,
                                      top: 80,
                                      child: Container(
                                        decoration: isEquip
                                            ? myLightGreenBoxDecoration
                                            : !isHave
                                                ? myNoneBoxDecoration
                                                : myWalkBoxDecoration,
                                        padding: EdgeInsets.all(6),
                                        child: Text12(
                                          text: isEquip
                                              ? "착용중"
                                              : isHave
                                                  ? "보유"
                                                  : "미보유",
                                          textColor: isHave ? myBlack : myGrey,
                                        ),
                                      )),
                                ],
                              ),
                            ),
                          );
                        }),
                      ),
                    )
                  ],
                ),
              ),
            )
          : Center(
              child: CircularProgressIndicator(),
            ),
    );
  }

  modal(index, id) {
    return BackdropFilter(
        filter: ImageFilter.blur(sigmaX: 3, sigmaY: 3), // 블러 효과 설정
        child: ModalContent(
          height: 350,
          customWidget: Column(
            children: [
              Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Image.asset(
                    "assets/item/$id.png",
                    width: 100,
                    height: 100,
                    filterQuality: FilterQuality.none,
                    fit: BoxFit.fitHeight,
                  ),
                  SizedBox(width: 10),
                  Text25(
                      text: info[setDetail.setId! - 1].item![index].itemName!,
                      bold: true),
                ],
              ),
              SizedBox(height: 20),
              Text16(text: ' 해당 아이템을', bold: true),
              Text16(
                  text:
                      '${info[setDetail.setId! - 1].item![index].itemName!}으로 변경할까요?',
                  bold: true),
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
                    onPressed: () async {
                      await InventoryService.fetchChangeItem(
                          context, setDetail.items![index].category!, id);
                      setState(() {
                        setDetail.items![index].isEquip = true;
                      });
                      if (!mounted) return;
                      Navigator.of(context).pop();
                    },
                    text: "변경",
                    active: true,
                    widthPadding: 50,
                  ),
                ],
              ),
              SizedBox(height: 10),
              Text12(text: "", textColor: myGrey),
            ],
          ),
        ));
  }
}
