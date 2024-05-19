import 'dart:async';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:forsythia/models/coins/coin_love.dart';
import 'package:forsythia/models/inventory/item_purchase.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/inventory/inventory_info.dart';
import 'package:forsythia/screens/inventory/inventory_screen.dart';
import 'package:forsythia/service/coin_service.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/large_app_bar.dart';
import 'package:forsythia/widgets/slide_page_route.dart';

class ItemScreen extends StatefulWidget {
  const ItemScreen({super.key});

  @override
  // ignore: library_private_types_in_public_api
  _ItemScreenState createState() => _ItemScreenState();
}

class _ItemScreenState extends State<ItemScreen>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;
  Item item = Item();
  String name = "";
  int coin = 0;
  int count = 0;
  bool active = false;

  @override
  void initState() {
    super.initState();
    loadCoin();
    _controller = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 200),
    );

    _animation = Tween(begin: 0.00, end: 0.08).animate(
      CurvedAnimation(parent: _controller, curve: Curves.easeInOut),
    )..addStatusListener((status) {
        if (status == AnimationStatus.completed) {
          _controller.reverse();
        } else if (status == AnimationStatus.dismissed) {
          _controller.forward();
        }
      });
  }

  Future<void> loadCoin() async {
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? info = await storageService.getLoginInfo();
    setState(() {
      coin = info?.coin ?? 0;
    });
  }

  Future<void> getItem() async {
    ItemPurchase itemPurchase =
        await InventoryService.fetchItemPurchase(context);
    setState(() {
      item = itemPurchase.data!;
      name = findItemNameById(item.id!)!;
      active = true;
    });
    SecureStorageService storageService = SecureStorageService();
    LoginInfo? info = await storageService.getLoginInfo();
    if (info != null) {
      info.coin = (info.coin! - 1000);
      storageService.saveLoginInfo(info);
    }
    loadCoin();
  }

  String? findItemNameById(int itemId) {
    for (var itemInfo in info) {
      for (var item in itemInfo.item!) {
        if (item.itemId == itemId) {
          return item.itemName;
        }
      }
    }
    return null;
  }

  List<String> images = [
    'assets/item_tier/tier_r.png',
    'assets/item_tier/tier_e.png',
    'assets/item_tier/tier_l.png',
  ];

  List<List<Color>> tierColors = [
    [
      Colors.grey.withOpacity(0.5),
      Colors.grey.shade600.withOpacity(0.5),
    ],
    [
      Color(0xff31DAFF).withOpacity(0.7),
      Color(0xff316BFF).withOpacity(0.7),
    ],
    [
      Color(0xffFFACDE).withOpacity(0.7),
      Color(0xffCA87FF).withOpacity(0.7),
    ],
    [
      Color(0xffAEECFF).withOpacity(0.7),
      Color(0xffF4FFAE).withOpacity(0.7),
      Color(0xffFFD361).withOpacity(0.7),
      Color(0xffFFA8F6).withOpacity(0.7),
    ]
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

  int? findItemTierColorById(int itemId, bool isHave) {
    if (isHave) {
      return 0;
    } else {
      if (itemId <= 30) {
        return 1;
      } else if (itemId < 42) {
        return 2;
      } else {
        return 3;
      }
    }
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(
        title: '뽑기',
        sentence: '엄청난 아이템을 뽑아보자!',
        coin: true,
        coinvalue: coin,
      ),
      body: Column(
        children: [
          SizedBox(height: 10),
          _goinventory(false),
          Expanded(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                _goldbox(),
                SizedBox(height: 50),
                !active ? _button() : SizedBox(width: 0),
                !active ? _coin() : SizedBox(width: 0),
                SizedBox(height: 70)
              ],
            ),
          )
        ],
      ),
    );
  }

  Widget _goinventory(pop) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        TextButton(
          onPressed: () {
            if (!active) {
              if (pop) {
                Navigator.pop(context);
              }
              Navigator.of(context)
                  .push(SlidePageRoute(nextPage: InventoryScreen()));
            }
          },
          child: Row(
            children: const [
              Image(
                image: AssetImage('assets/emoji/drawer.png'),
                width: 20,
                height: 20,
                fit: BoxFit.cover,
              ),
              Text16(text: '  보관함이동  ', bold: true),
            ],
          ),
        ),
      ],
    );
  }

  Widget _goldbox() {
    return GestureDetector(
      onTap: () async {
        setState(() {
          count++;
        });
        if (count == 50) {
          CoinLove coinLove = CoinLove(
              isIncreased: true, coinTitle: "PETCARE", coinAmount: 100);
          await CoinService.fetchPetLove(coinLove);
          SecureStorageService storageService = SecureStorageService();
          LoginInfo? info = await storageService.getLoginInfo();
          if (info != null) {
            info.coin = (info.coin! + 100);
            storageService.saveLoginInfo(info);
          }
          Fluttertoast.showToast(
            msg: '상자를 50번 클릭하셨군요!   \n 보상으로 100코인을 드립니다.',
            toastLength: Toast.LENGTH_SHORT,
            gravity: ToastGravity.CENTER,
            backgroundColor: myMainGreen,
          );
          loadCoin();
        }
      },
      child: AnimatedBuilder(
        animation: _animation,
        builder: (context, child) {
          return active
              ? Transform.rotate(
                  angle: _animation.value,
                  child: Image(
                    image: AssetImage('assets/images/goldbox.png'),
                    width: 250,
                    height: 250,
                    fit: BoxFit.cover,
                  ),
                )
              : Image(
                  image: AssetImage('assets/images/goldbox.png'),
                  width: 250,
                  height: 250,
                  fit: BoxFit.cover,
                );
        },
      ),
    );
  }

  Widget _button() {
    return GestureDetector(
      onTap: () {
        if (coin >= 1000) {
          getItem();
          _controller.reset(); // 애니메이션을 초기 상태로 재설정합니다.
          _controller.forward(); // 애니메이션을 시작합니다.
          Timer(Duration(seconds: 2), () {
            _showmodal(context);
          });
        } else {
          Fluttertoast.showToast(
            msg: '뽑기를 하기엔 돈이 부족합니다!',
            toastLength: Toast.LENGTH_SHORT,
            gravity: ToastGravity.CENTER,
            backgroundColor: myMainGreen,
          );
        }
      },
      child: Stack(
        alignment: Alignment.center,
        children: [
          Image.asset(
            'assets/images/pixelbox.png',
            width: 250,
          ),
          Text20(text: '1회 뽑기!', bold: true)
        ],
      ),
    );
  }

  Widget _coin() {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text20(text: '1000', bold: true),
          SizedBox(width: 5),
          Image.asset(
            'assets/color_icons/icon_coin.png',
            width: 25,
            height: 25,
            fit: BoxFit.cover,
          ),
        ],
      ),
    );
  }

  void _showmodal(BuildContext parentContext) {
    showDialog(
      context: parentContext,
      barrierDismissible: false,
      builder: (BuildContext context) {
        // dialogContext를 여기서 사용해서 나중에 다시 뽑기 버튼에서 사용해.
        return Stack(
          children: [
            GestureDetector(
              onTap: () {
                setState(() {
                  _animation = Tween(begin: 0.00, end: 0.0).animate(
                    CurvedAnimation(
                        parent: _controller, curve: Curves.easeInOut),
                  );
                });
                _controller.stop(); // 애니메이션 중지
              },
            ),
            Dialog(
              backgroundColor: myBackground,
              insetPadding: EdgeInsets.fromLTRB(30, 120, 30, 120),
              child: Container(
                height: 500,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(25)),
                  gradient: LinearGradient(
                    begin: Alignment.topLeft,
                    end: Alignment.bottomCenter,
                    colors: tierColors[
                        findItemTierColorById(item.id!, item.isHave!)!],
                  ),
                ),
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.all(10.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [_close()],
                      ),
                    ),
                    SizedBox(height: 10),
                    Expanded(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text36(
                              text: !item.isHave! ? 'NEW!!' : '중복', bold: true),
                          Image(
                            image: AssetImage('assets/item/${item.id!}.png'),
                            width: 200,
                            height: 200,
                            fit: BoxFit.contain,
                            filterQuality: FilterQuality.none,
                          ),
                          SizedBox(height: 20),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Image(
                                image: AssetImage(
                                    images[findItemTierById(item.id!)!]),
                                width: 25,
                                height: 25,
                                fit: BoxFit.cover,
                              ),
                              SizedBox(width: 5),
                              Text16(text: name, bold: true)
                            ],
                          ),
                          SizedBox(height: 20),
                          GestureDetector(
                            onTap: () {
                              Navigator.pop(context, "update");
                              setState(() {
                                active = false;
                              });
                              if (coin >= 1000) {
                                getItem();
                                _controller.reset(); // 애니메이션을 초기 상태로 재설정합니다.
                                _controller.forward(); // 애니메이션을 시작합니다.
                                Timer(Duration(seconds: 2), () {
                                  _showmodal(
                                      parentContext); // 다시 parentContext 사용
                                });
                              } else {
                                Fluttertoast.showToast(
                                  msg: '뽑기를 하기엔 돈이 부족합니다!',
                                  toastLength: Toast.LENGTH_SHORT,
                                  gravity: ToastGravity.CENTER,
                                  backgroundColor: myMainGreen,
                                );
                              }
                            },
                            child: Stack(
                              alignment: Alignment.center,
                              children: [
                                Image.asset(
                                  'assets/images/pixelbox.png',
                                  width: 250,
                                ),
                                Text20(text: '다시 뽑기', bold: true)
                              ],
                            ),
                          ),
                          SizedBox(height: 50)
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

  Widget _close() {
    return Row(
      children: [
        SizedBox(width: 10),
        InkWell(
          onTap: () {
            Navigator.pop(context, "update");
            setState(() {
              active = false;
            });
            // Navigator.pop(context, "update");
            // Navigator.push(
            //     context, MaterialPageRoute(builder: (context) => ItemScreen()));
          },
          child: Image.asset(
            'assets/icons/common_close.png',
            width: 20.0,
            height: 20.0,
          ),
        ),
        TextButton(
          onPressed: () {
            Navigator.pop(context, "update");
            setState(() {
              active = false;
            });
            // Navigator.pop(context, "update");
            // Navigator.push(
            //     context, MaterialPageRoute(builder: (context) => ItemScreen()));
          },
          child: Text16(
            text: "닫기",
            bold: true,
          ),
        ),
      ],
    );
  }
}
