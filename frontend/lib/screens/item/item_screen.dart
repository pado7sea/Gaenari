import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/inventory/inventory_screen.dart';
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

  final bool _new = true;
  int coin = 0;

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

  List<String> images = [
    'assets/item_tier/tier_e.png',
    'assets/item_tier/tier_l.png',
    'assets/item_tier/tier_r.png',
  ];

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
          _goinventory(),
          Expanded(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                _goldbox(),
                SizedBox(height: 50),
                _button(),
                _coin(),
                SizedBox(height: 70)
              ],
            ),
          )
        ],
      ),
    );
  }

  Widget _goinventory() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        TextButton(
          onPressed: () {
            Navigator.of(context)
                .push(SlidePageRoute(nextPage: InventoryScreen()));
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
    return AnimatedBuilder(
      animation: _animation,
      builder: (context, child) {
        return Transform.rotate(
          angle: _animation.value,
          child: Image(
            image: AssetImage('assets/images/goldbox.png'),
            width: 250,
            height: 250,
            fit: BoxFit.cover,
          ),
        );
      },
    );
  }

  Widget _button() {
    return GestureDetector(
      onTap: () {
        Timer(Duration(seconds: 3), () {
          _showmodal(context);
        });
        _controller.reset(); // 애니메이션을 초기 상태로 재설정합니다.
        _controller.forward(); // 애니메이션을 시작합니다.

        // 애니메이션을 멈추도록 예약
        Timer(Duration(milliseconds: 3100), () {
          setState(() {
            _animation = Tween(begin: 0.00, end: 0.0).animate(
              CurvedAnimation(parent: _controller, curve: Curves.easeInOut),
            );
          });
          _controller.stop(); // 애니메이션 중지
        });
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
          Image.asset(
            'assets/emoji/money.png',
            width: 25,
            height: 25,
            fit: BoxFit.fill,
          ),
          Text16(text: '100P', bold: true)
        ],
      ),
    );
  }

  void _showmodal(BuildContext context) {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return Stack(
          children: [
            // Background blur effect
            GestureDetector(
              onTap: () {
                Navigator.of(context)
                    .pop(); // Close the dialog on background tap
              },
              child: Container(
                decoration: BoxDecoration(
                  gradient: LinearGradient(
                    begin: Alignment.topCenter,
                    end: Alignment.bottomCenter,
                    colors: [
                      Colors.blue.withOpacity(0.5),
                      Colors.purple.withOpacity(0.5),
                    ],
                  ),
                ),
                child: BackdropFilter(
                  filter: ImageFilter.blur(
                      sigmaX: 5.0,
                      sigmaY:
                          5.0), // Adjust the sigma values for blur intensity
                  child: Container(
                    color: Colors.transparent,
                  ),
                ),
              ),
            ),
            // Dialog content
            Dialog(
              backgroundColor: myBackground,
              insetPadding: EdgeInsets.fromLTRB(30, 100, 30, 100),
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(10.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [_close(), _goinventory()],
                    ),
                  ),
                  SizedBox(height: 10),
                  Expanded(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text36(text: _new ? '새로운!' : '꽝', bold: true),
                        Image(
                          image: AssetImage('assets/images/goldbox.png'),
                          width: 200,
                          height: 200,
                          fit: BoxFit.cover,
                        ),
                        SizedBox(height: 20),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image(
                              image: AssetImage(images[0]),
                              width: 25,
                              height: 25,
                              fit: BoxFit.cover,
                            ),
                            Text16(text: ' 냄새나는 어쩌구', bold: true)
                          ],
                        ),
                        SizedBox(height: 20),
                        Stack(
                          alignment: Alignment.center,
                          children: [
                            Image.asset(
                              'assets/images/pixelbox.png',
                              width: 250,
                            ),
                            Text20(text: '다시 뽑기', bold: true)
                          ],
                        ),
                        SizedBox(height: 50)
                      ],
                    ),
                  )
                ],
              ),
            ),
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
            Navigator.of(context).pop();
          },
          child: Image.asset(
            'assets/icons/common_close.png',
            width: 20.0,
            height: 20.0,
          ),
        ),
        TextButton(
          onPressed: () {
            Navigator.of(context).pop();
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
