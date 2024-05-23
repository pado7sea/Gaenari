import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class ModalContent extends StatelessWidget {
  final Widget customWidget;
  final double height;

  const ModalContent(
      {super.key, required this.customWidget, required this.height});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: myBackground,
          borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20), topRight: Radius.circular(20))),
      width: double.infinity,
      height: height,
      padding: EdgeInsets.all(16),
      child: Column(
        children: [
          Row(
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
          ),
          customWidget, // 파라미터로 받은 위젯을 여기에 추가해줘
        ],
      ),
    );
  }
}


//이런식으로 쓰삼
// showModalBottomSheet(
//   context: context,
//   builder: (BuildContext context) {
//     return BackdropFilter(
//       filter: ImageFilter.blur(
//           sigmaX: 3, sigmaY: 3), // 블러 효과 설정
//       child: ModalContent(
//         height: 250,
//         customWidget: Column(
//           children: [
//             SizedBox(height: 20),
//             Text16(
//                 text: '${list[index].nickName}님에게',
//                 bold: true),
//             Text16(text: ' 친구신청을 보낼까요?', bold: true),
//             SizedBox(height: 20),
//             Row(
//               mainAxisAlignment:
//                   MainAxisAlignment.center,
//               children: [
//                 SmallButton(
//                   onPressed: () {
//                     Navigator.of(context).pop();
//                   },
//                   text: "취소",
//                   active: false,
//                   widthPadding: 50,
//                 ),
//                 SizedBox(
//                   width: 16,
//                 ),
//                 SmallButton(
//                   onPressed: () {
//                     _mateAdd(index);
//                   },
//                   text: "신청",
//                   active: true,
//                   widthPadding: 50,
//                 ),
//               ],
//             ),
//             SizedBox(height: 10),
//             Text12(
//                 text: "친구신청 후 요청취소가 불가합니다.",
//                 textColor: myGrey),
//           ],
//         ),
//       ),
//     );
//   },
// )