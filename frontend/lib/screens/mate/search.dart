import 'package:flutter/material.dart';
import 'package:flutter/services.dart'; // 이 부분을 추가해줘야 해

import 'package:forsythia/widgets/box_dacoration.dart';

class MateSearchBar extends StatefulWidget {
  final Function(String) onSearch;

  const MateSearchBar({super.key, required this.onSearch});

  @override
  MateSearchBarState createState() => MateSearchBarState();
}

class MateSearchBarState extends State<MateSearchBar> {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: myBorderBoxDecoration,
      padding: EdgeInsets.symmetric(horizontal: 16), // 검색창 좌우 마진
      child: Row(
        children: [
          Expanded(
            child: TextField(
              controller: _controller,
              inputFormatters: [
                FilteringTextInputFormatter.allow(
                    RegExp(r'[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣\s]')), // 특수문자 제한
              ],
              onSubmitted: (value) {
                widget.onSearch(value); // 검색어 전달
              },
              decoration: InputDecoration(
                hintText: '친구검색',
                border: InputBorder.none,
                hintStyle: TextStyle(color: Colors.grey),
              ),
            ),
          ),
          SizedBox(width: 8),
          GestureDetector(
            onTap: () {
              widget.onSearch(_controller.text); // 검색어 전달
            },
            child: Image.asset(
              "assets/icons/mate_search.png",
              filterQuality: FilterQuality.none,
              fit: BoxFit.cover,
            ),
          ),
        ],
      ),
    );
  }
}
