import 'package:flutter/material.dart';

class largeAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final String sentence;

  const largeAppBar({
    super.key,
    required this.title,
    required this.sentence,
  });

  // 앱바 높이 지정
  @override
  Size get preferredSize => Size.fromHeight(230);

  @override
  Widget build(BuildContext context) {
    return AppBar(
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
                image: AssetImage("assets/appbarImage.png"),
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
                Text(
                  title,
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 36,
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
                Padding(
                  padding: const EdgeInsets.symmetric(
                      vertical: 20.0, horizontal: 50.0),
                  child: Text(
                    sentence,
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
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
