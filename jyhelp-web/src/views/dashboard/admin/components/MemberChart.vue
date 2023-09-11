<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    autoResize: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      chart: null,
      chartData: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    setOptions() {
      this.chart.setOption({
        title: {
          text: '',
          x: 'center',
          y: 0,
          textStyle: {
            color: '#B4B4B4',
            fontSize: 16,
            fontWeight: 'normal'
          }
        },
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(0,0,0,0.6)',
          // formatter:"{b}:{c}"
          formatter: function(params) {
            let str = params[0].name + ' ' + params[0].data.stationName + '</br>'
            params.forEach((item) => {
              if (item.seriesName === '注册会员数' || item.seriesName === '回温') {
                str +=
                  item.marker +
                  item.seriesName +
                  ' : ' +
                  item.data.value +
                  ' ℃' +
                  '</br>'
              } else if (item.seriesName === '当日新增会员数') {
                // 柱状图渐变时设置marker
                item.marker =
                  '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#6C50F3;"></span>'
                str += item.marker + item.seriesName + ' : ' + item.data.value + ' m'
              }
            })
            return str
          }
        },
        legend: {
          // 修改legend的高度宽度
          itemHeight: 5,
          itemWidth: 24,
          data: [
            {
              name: '注册会员数',
              icon: 'rect' // legend的icon
            },
            {
              name: '当日新增会员数',
              icon: 'rect'
            }
          ],
          textStyle: {
            color: '#B4B4B4'
          },
          top: '3%',
          // 选择关闭的legend
          selected: {
            回温: false
          }
        },
        grid: {
          x: '8%',
          top: '12%',
          width: '85%',
          y: '5%'
        },
        xAxis: [
          {
            // type:'category',
            data: ['1km', '2km', '3km', '4km', '5km', '6km'],
            boundaryGap: true,
            axisLine: {
              lineStyle: {
                color: '#B4B4B4'
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        yAxis: [
          {
            name: '供回温度(℃）',
            nameLocation: 'middle',
            nameTextStyle: {
              padding: [3, 4, 50, 6]
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed',
                color: '#eee'
              }
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#333'
              },
              formatter: '{value} '
            }
          },
          {
            name: '当日新增会员数',
            nameLocation: 'middle',
            nameTextStyle: {
              padding: [50, 4, 5, 6]
            },
            splitLine: {
              show: false
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#333'
              },
              formatter: '{value} '
            }
          }
        ],
        series: [
          {
            name: '注册会员数',
            type: 'line',
            smooth: true,
            showSymbol: true,
            // 矢量画五角星
            symbol: 'path://M150 0 L80 175 L250 75 L50 75 L220 175 Z',
            symbolSize: 12,
            yAxisIndex: 0,
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: 'rgba(250,180,101,0.3)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(250,180,101,0)'
                  }
                ]),
                shadowColor: 'rgba(250,180,101,0.2)',
                shadowBlur: 20
              }
            },
            itemStyle: {
              normal: {
                color: '#FF8000'
              }
            },
            // data中可以使用对象，value代表相应的值，另外可加入自定义的属性
            data: [
              {
                value: 1,
                stationName: 's1'
              },
              {
                value: 3,
                stationName: 's2'
              },
              {
                value: 4,
                stationName: 's3'
              },
              {
                value: 9,
                stationName: 's4'
              },
              {
                value: 3,
                stationName: 's5'
              },
              {
                value: 2,
                stationName: 's6'
              }
            ]
          },
          {
            name: '回温',
            type: 'line',
            smooth: true,
            showSymbol: true,
            symbol: 'emptyCircle',
            symbolSize: 12,
            yAxisIndex: 0,
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: 'rgba(199, 237, 250,0.5)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(199, 237, 250,0.2)'
                    }
                  ],
                  false
                )
              }
            },
            itemStyle: {
              normal: {
                color: '#3bbc86'
              }
            },
            data: [
              {
                value: 31,
                stationName: 's1'
              },
              {
                value: 36,
                stationName: 's2'
              },
              {
                value: 54,
                stationName: 's3'
              },
              {
                value: 89,
                stationName: 's4'
              },
              {
                value: 73,
                stationName: 's5'
              },
              {
                value: 22,
                stationName: 's6'
              }
            ]
          },
          {
            name: '当日新增会员数',
            type: 'bar',
            barWidth: 30,
            yAxisIndex: 1,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: 'rgba(108,80,243,0.3)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(108,80,243,0)'
                  }
                ]),
                // 柱状图圆角
                barBorderRadius: [30, 30, 0, 0]
              }
            },

            data: [
              {
                value: 11,
                stationName: 's1'
              },
              {
                value: 34,
                stationName: 's2'
              },
              {
                value: 54,
                stationName: 's3'
              },
              {
                value: 39,
                stationName: 's4'
              },
              {
                value: 63,
                stationName: 's5'
              },
              {
                value: 24,
                stationName: 's6'
              }
            ]
          }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
