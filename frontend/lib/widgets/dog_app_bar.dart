import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class DogAppBar extends StatefulWidget implements PreferredSizeWidget {
  const DogAppBar({super.key});

  @override
  State<DogAppBar> createState() => _DogAppBarState();

  // 앱바 높이 지정
  @override
  Size get preferredSize => Size.fromHeight(55);
}

List<String> dogs = [
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

List<String> dogtier = [
  'assets/dog_tier/tier_BRONZE.png',
  'assets/dog_tier/tier_c.png',
  'assets/dog_tier/tier_d.png',
  'assets/dog_tier/tier_g.png',
  'assets/dog_tier/tier_p.png',
  'assets/dog_tier/tier_s.png',
];

class _DogAppBarState extends State<DogAppBar> {
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
                        children: [_heart(), _coin()],
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
          image: AssetImage(dogtier[0]),
          width: 22,
          height: 25,
          fit: BoxFit.cover,
          filterQuality: FilterQuality.none,
        ),
        Text20(text: '  만득이', bold: true)
      ],
    );
  }

  Widget _heart() {
    return Row(
      children: [
        Text16(text: '30  ', bold: true),
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

  Widget _coin() {
    return Row(
      children: [
        Text16(text: '  20.000  ', bold: true),
        Image(
          image: AssetImage('assets/color_icons/icon_coin.png'),
          width: 18,
          height: 18,
          fit: BoxFit.cover,
          filterQuality: FilterQuality.none,
        ),
        SizedBox(width: 10)
      ],
    );
  }
}
