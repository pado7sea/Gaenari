import 'package:flutter/material.dart';
import 'package:forsythia/widgets/box.dart';

class MateSearchBar extends StatelessWidget {
  const MateSearchBar({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: myBorderBoxDecoration,
      padding: EdgeInsets.symmetric(horizontal: 16), // 검색창 좌우 마진
      child: Row(
        children: [
          Expanded(
            child: TextField(
              decoration: InputDecoration(
                hintText: '당신에게 필요없는 친구검색기능',
                border: InputBorder.none,
                hintStyle: TextStyle(color: Colors.grey),
              ),
            ),
          ),
          SizedBox(width: 8),
          Image.asset(
            "assets/icons/mate_search.png",
            filterQuality: FilterQuality.none,
            fit: BoxFit.cover,
          ),
        ],
      ),
    );
  }
}
