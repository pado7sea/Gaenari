import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';

class smallAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;

  const smallAppBar({
    super.key,
    required this.title,
  });

  // 앱바 높이 지정
  @override
  Size get preferredSize => Size.fromHeight(55);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: myBackground,
      // 뒤로가기 버튼
      // leading: Padding(
      //   padding: const EdgeInsets.symmetric(horizontal: 20.0),
      //   child: IconButton(
      //     icon: Icon(Icons.arrow_back),
      //     onPressed: () {
      //       Navigator.of(context).pop(); // 이전 화면으로 이동
      //     },
      //   ),
      // ),

      // 앱바 이미지
      centerTitle: true,
      flexibleSpace: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage("assets/images/smallappbar.png"),
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
                  height: 20,
                ),
                Text(
                  title,
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                    shadows: [
                      Shadow(
                        blurRadius: 2.0,
                        color: Colors.grey,
                        offset: Offset(2.0, 2.0),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
