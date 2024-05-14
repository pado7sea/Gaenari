import 'package:flutter/material.dart';
import 'package:forsythia/screens/coin/coin_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:intl/intl.dart';

class DogAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String? tier;
  final String? name;
  final int? affection;
  final int? coinvalue;

  const DogAppBar({
    super.key,
    this.tier = "BRONZE",
    this.name = "만득이",
    this.affection = 0,
    this.coinvalue = 0,
  });

  // 앱바 높이 지정
  @override
  Size get preferredSize => Size.fromHeight(55);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: myBackground,

      // 앱바 이미지
      centerTitle: true,
      flexibleSpace: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage("assets/images/smallappbar.png"),
                filterQuality: FilterQuality.none,
                fit: BoxFit.fill,
              ),
            ),
          ),

          // 텍스트
          Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                SizedBox(
                  height: 40,
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      _doginfo(),
                      Row(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: [
                          _heart(),
                          GestureDetector(
                              onTap: () {
                                Navigator.of(context).push(
                                    SlidePageRoute(nextPage: CoinScreen()));
                              },
                              child: Row(
                                children: [
                                  SizedBox(width: 25),
                                  Text16(
                                      text: NumberFormat('#,###,###')
                                          .format(coinvalue),
                                      bold: true),
                                  SizedBox(width: 5),
                                  Image(
                                    image: AssetImage(
                                        'assets/color_icons/icon_coin.png'),
                                    width: 18,
                                    height: 18,
                                    fit: BoxFit.cover,
                                  ),
                                ],
                              ))
                        ],
                      )
                    ],
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _doginfo() {
    return Row(
      children: [
        Image(
          image: AssetImage("assets/dog_tier/tier_$tier.png"),
          width: 22,
          height: 25,
          fit: BoxFit.cover,
          filterQuality: FilterQuality.none,
        ),
        Text20(text: '  $name', bold: true)
      ],
    );
  }

  Widget _heart() {
    return Row(
      children: [
        Text16(text: '$affection  ', bold: true),
        Image(
          image: AssetImage('assets/color_icons/icon_love.png'),
          width: 18,
          height: 16,
          fit: BoxFit.cover,
          filterQuality: FilterQuality.none,
        ),
      ],
    );
  }
}
