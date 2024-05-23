import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class SmallAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final bool back;

  const SmallAppBar({
    super.key,
    required this.title,
    this.back = false,
  });

  // 앱바 높이 지정
  @override
  Size get preferredSize => Size.fromHeight(55);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: myBackground,

      // 뒤로가기 버튼
      leading: Padding(
        padding: const EdgeInsets.all(15),
        child: back
            ? GestureDetector(
                onTap: () {
                  Navigator.pop(context, "update"); // 이미지 클릭 시 뒤로 가기
                },
                child: Image.asset(
                  "assets/icons/common_back.png",
                  filterQuality: FilterQuality.none,
                  fit: BoxFit.cover,
                  width: 10,
                  height: 10,
                ),
              )
            : null,
      ),

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
                Text20(
                  text: title,
                  bold: true,
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
