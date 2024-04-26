import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/widgets/shadowImg.dart';

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
      backgroundColor: myBackground,
      // 뒤로가기 버튼
      leading: Padding(
        padding: const EdgeInsets.all(15.0),
        child: GestureDetector(
          onTap: () {
            Navigator.pop(context); // 이미지 클릭 시 뒤로 가기
          },
          child: Image.asset(
            "assets/icons/common_back.png",
            filterQuality: FilterQuality.none,
            fit: BoxFit.cover,
          ), // 여기에 네가 사용하는 이미지 경로 넣어줘
        ),
      ),

      // 앱바 이미지
      centerTitle: true,
      flexibleSpace: Stack(
        children: [
          ShadowImg(
            sigma: 5,
            offset: Offset(0, 5),
            child: Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage("assets/images/appbarImage.png"),
                  fit: BoxFit.fill,
                ),
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
                    shadows: const [
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
