import 'package:flutter/material.dart';
import 'package:forsythia/screens/coin/coin_screen.dart';
import 'package:forsythia/screens/inventory/inventory_screen.dart';
import 'package:forsythia/screens/item/item_screen.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/dog_app_bar.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:go_router/go_router.dart';

class DogHouseScreen extends StatefulWidget {
  const DogHouseScreen({super.key});

  @override
  State<DogHouseScreen> createState() => _DogHouseScreenState();
}

class _DogHouseScreenState extends State<DogHouseScreen> {
  bool zoomin = false;
  bool isToggled = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: zoomin ? null : DogAppBar(),
        body: zoomin
            ? Padding(
                padding: const EdgeInsets.all(20.0),
                child: Column(
                  children: [
                    SizedBox(height: 35),
                    _zoom(),
                    SizedBox(height: 10),
                    _togglebutton(),
                  ],
                ),
              )
            : Padding(
                padding: const EdgeInsets.all(20.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(height: 5),
                    _zoom(),
                    SizedBox(height: 10),
                    _togglebutton(),
                  ],
                ),
              ));
  }

  Widget _zoom() {
    return GestureDetector(
      onTap: () {
        setState(() {
          zoomin = !zoomin;
        });
      },
      child: Container(
        width: 50,
        height: 50,
        child: Center(
            child: zoomin
                ? Image(
                    image: AssetImage('assets/icons/zoomout.png'),
                    width: 18,
                    height: 18,
                    fit: BoxFit.cover,
                    filterQuality: FilterQuality.none,
                  )
                : Image(
                    image: AssetImage('assets/icons/zoomin.png'),
                    width: 18,
                    height: 18,
                    fit: BoxFit.cover,
                    filterQuality: FilterQuality.none,
                  )),
        decoration: BoxDecoration(
          color: Colors.white,
          shape: BoxShape.circle,
          boxShadow: [
            BoxShadow(
              color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 30%)
              // spreadRadius: 2, // 그림자 퍼지는 정도
              blurRadius: 15, // 그림자 흐림 정도
              offset: Offset(0, 10), // 그림자의 위치 (x, y)
            ),
          ],
        ),
      ),
    );
  }

  Widget _togglebutton() {
    return GestureDetector(
      onTap: () {
        setState(() {
          isToggled = !isToggled;
        });
      },
      child: isToggled
          ? Container(
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(left: 6),
                      child: Image(
                        image: AssetImage('assets/icons/common_up.png'),
                        width: 18,
                        height: 18,
                        fit: BoxFit.cover,
                        filterQuality: FilterQuality.none,
                      ),
                    ),
                    SizedBox(height: 20),
                    GestureDetector(
                        onTap: () {
                          Navigator.of(context).push(
                              SlidePageRoute(nextPage: InventoryScreen()));
                        },
                        child: Row(
                          mainAxisSize:
                              MainAxisSize.min, // Row의 가로 길이를 내부 요소만큼만 차지하도록 설정
                          children: [
                            Image(
                              image: AssetImage(
                                  'assets/icons/doghouse_drawer.png'),
                              width: 30,
                              height: 30,
                              fit: BoxFit.cover,
                              filterQuality: FilterQuality.none,
                            ),
                            Text16(text: ' 보관함   ')
                          ],
                        )),
                    SizedBox(height: 15),
                    GestureDetector(
                        onTap: () {
                          Navigator.of(context)
                              .push(SlidePageRoute(nextPage: ItemScreen()));
                        },
                        child: Row(
                          mainAxisSize:
                              MainAxisSize.min, // Row의 가로 길이를 내부 요소만큼만 차지하도록 설정
                          children: [
                            Image(
                              image: AssetImage(
                                  'assets/icons/doghouse_random.png'),
                              width: 30,
                              height: 30,
                              fit: BoxFit.cover,
                              filterQuality: FilterQuality.none,
                            ),
                            Text16(text: ' 뽑기   ')
                          ],
                        )),
                    SizedBox(height: 5),
                  ],
                ),
              ),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(15),
                boxShadow: [
                  BoxShadow(
                    color:
                        Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 30%)
                    // spreadRadius: 2, // 그림자 퍼지는 정도
                    blurRadius: 15, // 그림자 흐림 정도
                    offset: Offset(0, 10), // 그림자의 위치 (x, y)
                  ),
                ],
              ),
            )
          : Container(
              width: 50,
              height: 50,
              child: Center(
                  child: Image(
                image: AssetImage('assets/icons/common_down.png'),
                width: 18,
                height: 18,
                fit: BoxFit.cover,
                filterQuality: FilterQuality.none,
              )),
              decoration: BoxDecoration(
                color: Colors.white,
                shape: BoxShape.circle,
                boxShadow: [
                  BoxShadow(
                    color:
                        Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 30%)
                    // spreadRadius: 2, // 그림자 퍼지는 정도
                    blurRadius: 15, // 그림자 흐림 정도
                    offset: Offset(0, 10), // 그림자의 위치 (x, y)
                  ),
                ],
              ),
            ),
    );
  }
}
